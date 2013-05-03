package edu.vt.wuvt.androidwuvt.mediaplayer;

import android.content.Context;
import edu.vt.wuvt.androidwuvt.R;
import edu.vt.wuvt.androidwuvt.view.mainactivity.MainActivity;



public class WuvtNotificationConfig implements IMediaPlayerServiceNotificationConfig {

	@Override
	public int getIconId() {
		return R.drawable.wuvt48;
	}

	@Override
	public Class<?> getActivityClass() {
		return MainActivity.class;
	}

	@Override
	public CharSequence getContentTitle() {
		return "WUVT4Android";
	}

	@Override
	public CharSequence getContextText() {
		return "Playing";
	}

	@Override
	public CharSequence getTickerText() {
		return "WUVT4Android";
	}

}
