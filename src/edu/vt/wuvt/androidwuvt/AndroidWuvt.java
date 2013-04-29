package edu.vt.wuvt.androidwuvt;

import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer.WuvtPlayerReadyListener;
import android.app.Application;

public class AndroidWuvt extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
//		WuvtMediaPlayer wuvtPlayer = WuvtMediaPlayer.get(this);
//		wuvtPlayer.prepare(new WuvtPlayerReadyListener() {
//			
//			@Override
//			public void ready() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}

}
