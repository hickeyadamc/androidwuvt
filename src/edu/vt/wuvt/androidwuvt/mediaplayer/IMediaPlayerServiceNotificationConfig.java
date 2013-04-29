package edu.vt.wuvt.androidwuvt.mediaplayer;

public interface IMediaPlayerServiceNotificationConfig {

	int getIconId();

	Class<?> getActivityClass();

	CharSequence getContentTitle();

	CharSequence getContextText();

	CharSequence getTickerText();

}
