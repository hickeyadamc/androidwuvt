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
	public interface WuvtIsPlayingListener {
		public void isPlaying(boolean playing);
	}
	public final static String TAG = WuvtMediaPlayer.class.getName();
	private final static String WUVT_URL = "http://engine.collegemedia.vt.edu:8000/wuvt.ogg";
	
	
	private final Context mContext;
	private List<WuvtPlayerReadyListener> mReadyListeners = new ArrayList<WuvtPlayerReadyListener>();
	private List<WuvtIsPlayingListener> mWuvtPlayingListeners = new ArrayList<WuvtIsPlayingListener>();
	private static WuvtMediaPlayer mInstance = null;
	
	private static boolean mPrepareStarted = false;
	private static boolean mPrepareFinished = false;
	
	private List<BroadcastReceiver> mBroadcastReceivers = new ArrayList<BroadcastReceiver>();
	
	/*
	 * Get the instance of WuvtMediaPlayer
	 */
	public static WuvtMediaPlayer get(Context context) {
		if(mInstance == null) {
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
		initPlayerReadyReceiver();
		initPlayingStatusReceiver();

	}
	
	private void initPlayingStatusReceiver() {
		BroadcastReceiver isPlayingReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				boolean playingStatus = intent.getBooleanExtra(MediaPlayerService.MUSIC_PLAYING_STATUS_KEY, false);
				for(WuvtIsPlayingListener playingListener : mWuvtPlayingListeners) {
					playingListener.isPlaying(playingStatus);
				}
				mWuvtPlayingListeners.clear();
				
			}
			
		};
		mBroadcastReceivers.add(isPlayingReceiver);
		mContext.registerReceiver(isPlayingReceiver, new IntentFilter(MediaPlayerService.SEND_MUSIC_PLAYING_STATUS));
		
	}
	private void startMediaPlayer() {
		if(mPrepareStarted == false) {
			Intent startMediaPlayerIntent = new Intent(mContext,MediaPlayerService.class);
			startMediaPlayerIntent.putExtra(MediaPlayerService.MUSIC_URL_KEY, WUVT_URL);
			mContext.startService(startMediaPlayerIntent);		
			mPrepareStarted = true;
		} else {
			if(mPrepareFinished == true) {
				playerReadyReceived();
			} else {
				return;
			}
		}
		
	}

	private void initPlayerReadyReceiver() {
		BroadcastReceiver playerReadyReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				playerReadyReceived();
				
			}
			
		};
		mBroadcastReceivers.add(playerReadyReceiver);
		mContext.registerReceiver(playerReadyReceiver, new IntentFilter(MediaPlayerService.SEND_BROADCAST_PREPARED));
		
	}
	
	private void playerReadyReceived() {
		mPrepareFinished = true;
		for(WuvtPlayerReadyListener readyListener : mReadyListeners) {
			readyListener.ready();
		}
		mReadyListeners.clear();
	}
	public void isPlaying(WuvtIsPlayingListener listener) {
		mWuvtPlayingListeners.add(listener);
		requestPlayingStatus();
	}

	private void requestPlayingStatus() {
		Intent requestPlayingStatus = new Intent(MediaPlayerService.REQUEST_PLAYING_STATUS);
		mContext.sendBroadcast(requestPlayingStatus);
		
	}
	public void play() {
		Intent playIntent = new Intent(MediaPlayerService.RECEIVE_BROADCAST_PLAY_MUSIC);
		mContext.sendBroadcast(playIntent);
	}
	public void pause() {
		Intent pauseIntent = new Intent(MediaPlayerService.RECEIVE_BROADCAST_PAUSE_MUSIC);
		mContext.sendBroadcast(pauseIntent);
	}
	public void stop() {
		for(BroadcastReceiver receiver : mBroadcastReceivers) {
			mContext.unregisterReceiver(receiver);
		}
		mBroadcastReceivers.clear();
		Intent stopMediaPlayerServiceIntent = new Intent(mContext,MediaPlayerService.class);
		mContext.stopService(stopMediaPlayerServiceIntent);
		mPrepareStarted = false;
		mPrepareFinished = false;
		
		
	}
	public boolean isPrepareStarted() {
		return mPrepareStarted;
	}
	public boolean isPrepareFinished() {
		return mPrepareFinished;
	}

}
