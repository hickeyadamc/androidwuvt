package edu.vt.wuvt.androidwuvt.view.mainactivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import edu.vt.wuvt.androidwuvt.R;
import edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.favorites.FavoritesFragment;
import edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.nowplaying.NowPlayingFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;


public class MainActivity extends SherlockFragmentActivity {
    ViewPager mViewPager;
    TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        instantiateMemberVariables();
        setupActionBar();
        createTabs(savedInstanceState);

    }

    private void createTabs(Bundle savedInstanceState) {
        mTabsAdapter = new TabsAdapter(this, mViewPager);
        final com.actionbarsherlock.app.ActionBar bar = getSupportActionBar();
        mTabsAdapter.addTab(bar.newTab().setText("Simple"),
                FavoritesFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Cursor"),
                NowPlayingFragment.class, null);

        if (savedInstanceState != null) {
        	//Use saved instance state to set currentTabe
//            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
		
	}

	private void setupActionBar() {
        final com.actionbarsherlock.app.ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		
	}

	private void instantiateMemberVariables() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager1);
		
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
