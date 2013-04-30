package edu.vt.wuvt.androidwuvt.view.mainactivity;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import edu.vt.wuvt.androidwuvt.utils.httputils.AsyncHttpUtils;
import edu.vt.wuvt.androidwuvt.utils.httputils.AsyncHttpUtils.AsyncHttpErrorListener;
import edu.vt.wuvt.androidwuvt.utils.httputils.AsyncHttpUtils.AsyncHttpListener;

public class MainActivity extends SherlockActivity {
	
	private Button play;
	private Button pause;
	private Button stop;
	public static final String TAG = MainActivity.class.getName();
	
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
		
		AsyncHttpUtils.doGet("http://www.google.com", new AsyncHttpListener() {


			@Override
			public void onResponse(String response) {
				Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
				
			}
		}, new AsyncHttpErrorListener() {
			
			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
