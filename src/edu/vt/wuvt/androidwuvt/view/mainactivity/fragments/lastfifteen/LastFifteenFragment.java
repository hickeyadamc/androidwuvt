package edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.lastfifteen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import edu.vt.wuvt.androidwuvt.R;

public class LastFifteenFragment extends SherlockFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_coming_soon, container,false);
	}

}
