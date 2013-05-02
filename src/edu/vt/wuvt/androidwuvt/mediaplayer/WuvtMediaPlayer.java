package edu.vt.wuvt.androidwuvt.mediaplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
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
	private static WuvtMediaPlayer mInstance = null;
	
//	private static boolean mPrepareStarted = false;
//	private static boolean mPrepareFinished = false;
	
	private static PlayingStatus sStatus = null;
	
	private List<BroadcastReceiver> mBroadcastReceivers = new ArrayList<BroadcastReceiver>();
	
	/*
	 * Get the instance of WuvtMediaPlayer
	 */
	public static WuvtMediaPlayer get(Context context) {
		if(mInstance == null) {
			sStatus = PlayingStatus.Stopped;
			mInstance = new WuvtMediaPlayer(context);
		}
		return mInstance;
	}
	public void prepare(WuvtPlayerReadyListener listener) {
		mReadyListeners.add(listener);
		startMediaPlayer();
	}
	
	private WuvtMediaPlayer(Context context) {
		mContext = context;
		

	}
	private void startMediaPlayer() {
		if(sStatus == PlayingStatus.Stopped) {
			Intent startMediaPlayerIntent = new Intent(mContext,MediaPlayerService.class);
			startMediaPlayerIntent.putExtra(MediaPlayerService.MUSIC_URL_KEY, WUVT_URL);
			initPlayerReadyReceiver();
			mContext.startService(startMediaPlayerIntent);		
			sStatus = PlayingStatus.Loading;
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
		sStatus = PlayingStatus.Paused;
		for(WuvtPlayerReadyListener readyListener : mReadyListeners) {
			readyListener.ready();
		}
		mReadyListeners.clear();
	}
	public void play() {
		if(sStatus == PlayingStatus.Paused) {
			Intent playIntent = new Intent(MediaPlayerService.RECEIVE_BROADCAST_PLAY_MUSIC);
			mContext.sendBroadcast(playIntent);
			sStatus = PlayingStatus.Playing;
		} else {
			Log.e(TAG, "Couldn't play. Status is: " + sStatus);
		}
	}
	public void pause() {
		if(sStatus == PlayingStatus.Playing) {
			Intent pauseIntent = new Intent(MediaPlayerService.RECEIVE_BROADCAST_PAUSE_MUSIC);
			mContext.sendBroadcast(pauseIntent);
			sStatus = PlayingStatus.Paused;
		} else {
			Log.e(TAG,"Couldn't pause. Status is: " + sStatus);
		}
	}
	public void stop() {
		
		for(BroadcastReceiver receiver : mBroadcastReceivers) {
			mContext.unregisterReceiver(receiver);
		}
		mBroadcastReceivers.clear();
		sStatus = PlayingStatus.Stopped;
		Intent stopMediaPlayerServiceIntent = new Intent(mContext,MediaPlayerService.class);
		mContext.stopService(stopMediaPlayerServiceIntent);
		
		
	}
	public PlayingStatus getStatus() {
		return sStatus;
	}
	

}
