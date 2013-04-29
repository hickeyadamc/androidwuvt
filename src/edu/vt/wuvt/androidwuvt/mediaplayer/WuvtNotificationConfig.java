package edu.vt.wuvt.androidwuvt.mediaplayer;

import android.content.Context;
import edu.vt.wuvt.androidwuvt.R;
import edu.vt.wuvt.androidwuvt.view.mainactivity.MainActivity;



public class WuvtNotificationConfig implements IMediaPlayerServiceNotificationConfig {

	@Override
	public int getIconId() {
		return R.drawable.ic_launcher;
	}

	@Override
	public Class<?> getActivityClass() {
		return MainActivity.class;
	}

	@Override
	public CharSequence getContentTitle() {
		return "Temp Content Title";
	}

	@Override
	public CharSequence getContextText() {
		return "Temp Context Text";
	}

	@Override
	public CharSequence getTickerText() {
		return "Temp Ticker Text";
	}

}
