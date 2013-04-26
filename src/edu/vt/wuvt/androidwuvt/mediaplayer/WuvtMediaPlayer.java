package edu.vt.wuvt.androidwuvt.mediaplayer;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

public class WuvtMediaPlayer {
	private final static MediaPlayer MEDIA_PLAYER = new MediaPlayer();
	private final static String WUVT_URL = "http://engine.collegemedia.vt.edu:8000/wuvt.ogg";
	public final static String TAG = WuvtMediaPlayer.class.getName();
	
	public static void prepare(MediaPlayer.OnPreparedListener listener) {
		try {
			MEDIA_PLAYER.setDataSource(WUVT_URL);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		} catch (SecurityException e) {
			Log.e(TAG, e.getMessage());
		} catch (IllegalStateException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		MEDIA_PLAYER.prepareAsync();
		MEDIA_PLAYER.setOnPreparedListener(listener);
	}
	
	public static void start() {
		MEDIA_PLAYER.start();
	}
	public static void pause() {
		MEDIA_PLAYER.pause();
	}
	public static boolean isPlaying() {
		return MEDIA_PLAYER.isPlaying();
	}
	public static void setErrorListener(MediaPlayer.OnErrorListener errorListener) {
		MEDIA_PLAYER.setOnErrorListener(errorListener);
	}

}
