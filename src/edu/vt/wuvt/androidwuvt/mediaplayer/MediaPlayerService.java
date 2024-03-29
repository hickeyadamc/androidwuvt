package edu.vt.wuvt.androidwuvt.mediaplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.TrackInfo;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MediaPlayerService extends Service {
	public enum MediaPlayerServiceStatus {
		Playing, Paused, Loading
	}

	public static final String TAG = MediaPlayerService.class.getName();
	
	protected static final String SEND_BROADCAST_PREPARED = "MediaPlayerServiceSendBroadCastPrepared";
	protected static final String MUSIC_URL_KEY = "MediaPlayerServiceMusicUrlKey";
	protected static final String RECEIVE_BROADCAST_PLAY_MUSIC = "MediaPlayerReceivePlayMusic";
	protected static final String RECEIVE_BROADCAST_PAUSE_MUSIC = "MediaPlayerReceivePauseMusic";
	private static final int NOTIFICATION_ID = 1;

	
	
	private MediaPlayer mMediaPlayer;
	private List<BroadcastReceiver> mBroadcastReceivers = new ArrayList<BroadcastReceiver>();
	private IMediaPlayerServiceNotificationConfig mNotificationConfig = new WuvtNotificationConfig();
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG,"OnStartCommand");
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
			initNotification();
		} else {
			return;
		}
	}
	private void pause() {
		if(mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
			cancelNotification();
//			startShutdownTimer();
		} else {
			return;
		}
	}



	private void initMediaPlayer(Intent startUpIntent) {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.reset();
		try {
			String musicUrl = startUpIntent.getExtras().getString(MUSIC_URL_KEY);
			if(musicUrl == null) {
				Log.e(TAG,"MusicUrl was null");
				return;
			}
			mMediaPlayer.setDataSource(musicUrl);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					Log.d(TAG,"MusicPlayerPrepared");
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
		super.onDestroy();
		for(BroadcastReceiver receiver : mBroadcastReceivers) {
			unregisterReceiver(receiver);
		}
		mBroadcastReceivers.clear();
		cancelNotification();
		mMediaPlayer.reset();
		mMediaPlayer.release();
	}

	private void cancelNotification() {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		mNotificationManager.cancel(NOTIFICATION_ID);
		
	}



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void initNotification() {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = mNotificationConfig.getIconId();
		CharSequence tickerText = mNotificationConfig.getTickerText();
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon,tickerText,when);
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		CharSequence contentTitle = mNotificationConfig.getContentTitle();
		CharSequence contentText = mNotificationConfig.getContextText();
		Context context = getApplicationContext();
		Intent notificationIntent = new Intent(context, mNotificationConfig.getActivityClass());
		PendingIntent contentIntent = PendingIntent.getActivity(this,  0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID , notification);
	
}

}
