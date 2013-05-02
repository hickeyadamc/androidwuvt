package edu.vt.wuvt.androidwuvt.view.mainactivity;

import android.content.Context;
import android.util.Log;

import com.actionbarsherlock.view.MenuItem;

import edu.vt.wuvt.androidwuvt.R;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer.PlayingStatus;

public class IconController {
	private Context mContext;
	private MenuItem mPlayPauseButton;
	private MenuItem mStopButton;
	private MenuItem mLoading;
	
	private boolean mIsPlaying = false;
	public static final String TAG = IconController.class.getName();

	public IconController(Context context, final MenuItem playPause,MenuItem stop,MenuItem loading) {
		
		mContext = context;
		mPlayPauseButton = playPause;
		mStopButton = stop;
		mLoading = loading;
	}
	
	public void update(PlayingStatus status) {
		if(status == PlayingStatus.Playing) {
			mLoading.setVisible(false);
			mPlayPauseButton.setVisible(true);
			mPlayPauseButton.setIcon(mContext.getResources().getDrawable(R.drawable.pause_white));
		} else if (status == PlayingStatus.Paused) {
			mPlayPauseButton.setVisible(true);
			mLoading.setVisible(false);
			mPlayPauseButton.setIcon(mContext.getResources().getDrawable(R.drawable.play_white));
		} else if (status == PlayingStatus.Loading) {
			mPlayPauseButton.setVisible(false);
			mLoading.setVisible(true);
		} else if (status == PlayingStatus.Stopped){
			mPlayPauseButton.setVisible(true);
			mPlayPauseButton.setIcon(mContext.getResources().getDrawable(R.drawable.play_white));
			mLoading.setVisible(false);
		} else {
			Log.d(TAG,"Bad state.");
		}
	}

}
