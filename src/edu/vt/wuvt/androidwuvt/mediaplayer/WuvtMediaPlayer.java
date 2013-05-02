package edu.vt.wuvt.androidwuvt.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class WuvtMediaPlayer {
	public interface WuvtPlayerReadyListener {
		public void ready();
	}
	public enum PlayingStatus {
		Playing, Paused, Stopped, Loading
	}
	public final static String TAG = WuvtMediaPlayer.class.getName();
	private final static String WUVT_URL = "http://engine.collegemedia.vt.edu:8000/wuvt.ogg";
	
	
	private final Context mContext;
	private List<WuvtPlayerReadyListener> mReadyListeners = new ArrayList<WuvtPlayerReadyListener>();
	
	
	private PlayingStatus mStatus = PlayingStatus.Stopped;
	
	private List<BroadcastReceiver> mBroadcastReceivers = new ArrayList<BroadcastReceiver>();
	
	public void prepare(WuvtPlayerReadyListener listener) {
		mReadyListeners.add(listener);
		startMediaPlayer();
	}
	
	public WuvtMediaPlayer(Context context) {
		mContext = context;
		boolean serviceIsRunning = isMediaPlayerServiceRunning();
		
		if(serviceIsRunning) {
			initPlayerReadyReceiver();
			
			//see onStopCalled()
			//it's assumed that we killed the service if it were in a state other than playing 
			//Therefore, if this object is instantiated and the service is running, the state must be playing
			mStatus = PlayingStatus.Playing;
		}
		
	}

	private void startMediaPlayer() {
		if(mStatus == PlayingStatus.Stopped) {
			Intent startMediaPlayerIntent = new Intent(mContext,MediaPlayerService.class);
			startMediaPlayerIntent.putExtra(MediaPlayerService.MUSIC_URL_KEY, WUVT_URL);
			initPlayerReadyReceiver();
			mContext.startService(startMediaPlayerIntent);		
			mStatus = PlayingStatus.Loading;
		}
		
	}

	private void initPlayerReadyReceiver() {
		BroadcastReceiver playerReadyReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d(TAG,"PlayerReady Broadcast received");
				playerReadyReceived();
				
			}
			
		};
		mBroadcastReceivers.add(playerReadyReceiver);
		mContext.registerReceiver(playerReadyReceiver, new IntentFilter(MediaPlayerService.SEND_BROADCAST_PREPARED));
		
	}
	
	private void playerReadyReceived() {
		mStatus = PlayingStatus.Paused;
		for(WuvtPlayerReadyListener readyListener : mReadyListeners) {
			readyListener.ready();
		}
		mReadyListeners.clear();
	}
	public void play() {
		if(mStatus == PlayingStatus.Paused) {
			Intent playIntent = new Intent(MediaPlayerService.RECEIVE_BROADCAST_PLAY_MUSIC);
			mContext.sendBroadcast(playIntent);
			mStatus = PlayingStatus.Playing;
		} else {
			Log.e(TAG, "Couldn't play. Status is: " + mStatus);
		}
	}
	public void pause() {
		if(mStatus == PlayingStatus.Playing) {
			Intent pauseIntent = new Intent(MediaPlayerService.RECEIVE_BROADCAST_PAUSE_MUSIC);
			mContext.sendBroadcast(pauseIntent);
			mStatus = PlayingStatus.Paused;
		} else {
			Log.e(TAG,"Couldn't pause. Status is: " + mStatus);
		}
	}
	public void stop() {
		unregisterAllReceivers();
		mStatus = PlayingStatus.Stopped;
		Intent stopMediaPlayerServiceIntent = new Intent(mContext,MediaPlayerService.class);
		mContext.stopService(stopMediaPlayerServiceIntent);
		
		
	}
	public PlayingStatus getStatus() {
		return mStatus;
	}
	public void onStopCalled() {
		Log.d(TAG,"onStopCalled");
		if(mStatus == PlayingStatus.Playing) {
			unregisterAllReceivers();
		} else {
			stop();
		}
		
	}
	private void unregisterAllReceivers() {
		for(BroadcastReceiver receiver : mBroadcastReceivers) {
			mContext.unregisterReceiver(receiver);
		}
		mBroadcastReceivers.clear();
		
	}
	private boolean isMediaPlayerServiceRunning() {
	    ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (MediaPlayerService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	

}
