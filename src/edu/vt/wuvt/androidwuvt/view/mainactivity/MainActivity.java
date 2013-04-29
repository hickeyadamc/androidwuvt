package edu.vt.wuvt.androidwuvt.view.mainactivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import edu.vt.wuvt.androidwuvt.R;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer.WuvtIsPlayingListener;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer.WuvtPlayerReadyListener;

public class MainActivity extends SherlockActivity {
	
	private Button play;
	private Button pause;
	private Button stop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final WuvtIsPlayingListener playerListener = new WuvtIsPlayingListener() {
			
			@Override
			public void isPlaying(boolean playing) {
				Toast.makeText(MainActivity.this, "Wuvt is playing: " + playing, Toast.LENGTH_SHORT).show();
				
			}
		};
		final WuvtMediaPlayer wuvtPlayer = WuvtMediaPlayer.get(this);
		wuvtPlayer.prepare(new WuvtPlayerReadyListener() {
			
			@Override
			public void ready() {
				ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
				pb.setVisibility(View.INVISIBLE);
				wuvtPlayer.isPlaying(playerListener);
				
			}
		});
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				wuvtPlayer.play();
				wuvtPlayer.isPlaying(playerListener);
			}
		});
		pause = (Button) findViewById(R.id.pause);
		pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				wuvtPlayer.pause();
				wuvtPlayer.isPlaying(playerListener);
			}
		});
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				wuvtPlayer.stop();
				wuvtPlayer.isPlaying(playerListener);
			}
		});
	}

}
