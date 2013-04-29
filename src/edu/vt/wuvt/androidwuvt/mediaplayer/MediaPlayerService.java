package edu.vt.wuvt.androidwuvt.mediaplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;

public class MediaPlayerService extends Service {
	public static final String TAG = MediaPlayerService.class.getName();
	public static final String SEND_BROADCAST_PREPARED = "MediaPlayerServiceSendBroadCastPrepared";
	public static final String MUSIC_URL_KEY = "MediaPlayerServiceMusicUrlKey";
	public static final String REQUEST_PLAYING_STATUS = "MediaPlayerIsPlayingStatusRequest";
	public static final String SEND_MUSIC_PLAYING_STATUS = "MediaPlayerSendMusicPlayingStatus";
	public static final String MUSIC_PLAYING_STATUS_KEY = "MediaPlayerMusicPlayingStatusKey";
	public static final String RECEIVE_BROADCAST_PLAY_MUSIC = "MediaPlayerReceivePlayMusic";
	public static final String RECEIVE_BROADCAST_PAUSE_MUSIC = "MediaPlayerReceivePauseMusic";
	
	private MediaPlayer mMediaPlayer;
	private List<BroadcastReceiver> mBroadcastReceivers = new ArrayList<BroadcastReceiver>();
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initMediaPlayer(intent);
		initPlayReceiver();		
		initPauseReceiver();
		return START_STICKY;
	}

	private void initPauseReceiver() {
		BroadcastReceiver pauseReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				pause();
				
			}
			
		};
		mBroadcastReceivers.add(pauseReceiver);
		registerReceiver(pauseReceiver, new IntentFilter(RECEIVE_BROADCAST_PAUSE_MUSIC));
		
	}
	private void initPlayReceiver() {
		BroadcastReceiver playReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				play();
				
			}
			
		};
		mBroadcastReceivers.add(playReceiver);
		registerReceiver(playReceiver, new IntentFilter(RECEIVE_BROADCAST_PLAY_MUSIC));
		
	}

	private void play() {
		if(!mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
		} else {
			return;
		}
	}
	private void pause() {
		if(mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		} else {
			return;
		}
	}

	private void initMediaPlayer(Intent startUpIntent) {
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(startUpIntent.getExtras().getString(MUSIC_URL_KEY));
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					broadcastPrepareFinished();
					
				}

				private void broadcastPrepareFinished() {
					Intent preparedIntent = new Intent(MediaPlayerService.SEND_BROADCAST_PREPARED);
					sendBroadcast(preparedIntent);
					
				}

			});
		} catch (IllegalArgumentException e) {
			Log.e(TAG,e.getMessage());
		} catch (SecurityException e) {
			Log.e(TAG,e.getMessage());
		} catch (IllegalStateException e) {
			Log.e(TAG,e.getMessage());
		} catch (IOException e) {
			Log.e(TAG,e.getMessage());
		}
		
	}
	
	@Override
	public void onDestroy() {
		for(BroadcastReceiver receiver : mBroadcastReceivers) {
			unregisterReceiver(receiver);
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
