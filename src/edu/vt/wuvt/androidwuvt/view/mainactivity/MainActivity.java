package edu.vt.wuvt.androidwuvt.view.mainactivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import edu.vt.wuvt.androidwuvt.R;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer.PlayingStatus;
import edu.vt.wuvt.androidwuvt.mediaplayer.WuvtMediaPlayer.WuvtPlayerReadyListener;
import edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.favorites.FavoritesFragment;
import edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.lastfifteen.LastFifteenFragment;
import edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.nowplaying.NowPlayingFragment;
import edu.vt.wuvt.androidwuvt.view.mainactivity.fragments.request.RequestFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ProgressBar;


public class MainActivity extends SherlockFragmentActivity {
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private IconController mIconController;
    private WuvtMediaPlayer mPlayer;
    
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        instantiateMemberVariables();
        setupActionBar();
        createTabs(savedInstanceState);
   

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  	
    	initMenu(menu);
    	initPlayer();
     	
    	return true;
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	mPlayer.onStopCalled();
    }

    private void initPlayer() {
        mPlayer = new WuvtMediaPlayer(this);
        if(mPlayer.getStatus() == PlayingStatus.Stopped) {
        	preparePlayer();
        } else {
        	mIconController.update(mPlayer.getStatus());
        }
		
	}

	private void initMenu(Menu menu) {
    	MenuInflater inflater = getSupportMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	MenuItem playPause = menu.findItem(R.id.play_pause);
    	MenuItem stop = menu.findItem(R.id.stop);    	
    	MenuItem loading = menu.findItem(R.id.playerLoadingMenuItem);
    	loading.setActionView(R.layout.action_bar_indeterminate_progress);
    	mIconController = new IconController(this,playPause,stop,loading);
    	
    	playPause.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				onPlayPauseClicked();
				return true;
			}
		});
    	
    	stop.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				onStopClicked();
				return true;
			}
		});
		
	}

	private void onStopClicked() {
		stopMusic();
	}

	private void onPlayPauseClicked() {
		PlayingStatus playingStatus = mPlayer.getStatus();
		if(playingStatus == PlayingStatus.Paused) {
			playMusic();
		} else if (playingStatus == PlayingStatus.Playing) {
			pauseMusic();
		} else if (playingStatus == PlayingStatus.Stopped) {
			preparePlayer();
		}
	}
	
	private void preparePlayer() {
		mIconController.update(PlayingStatus.Loading);
		mPlayer.prepare(new WuvtPlayerReadyListener() {
			
			@Override
			public void ready() {
				mIconController.update(PlayingStatus.Paused);
				
			}
		});
		
	}

	private void playMusic() {
		mPlayer.play();
		mIconController.update(PlayingStatus.Playing);
	}
	private void pauseMusic() {
		mPlayer.pause();
		mIconController.update(PlayingStatus.Paused);
	}
	private void stopMusic() {
		mPlayer.stop();
		mIconController.update(PlayingStatus.Stopped);
	}

	private void createTabs(Bundle savedInstanceState) {
        mTabsAdapter = new TabsAdapter(this, mViewPager);
        final com.actionbarsherlock.app.ActionBar bar = getSupportActionBar();
        mTabsAdapter.addTab(bar.newTab().setText("Now Playing"),
                NowPlayingFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Last Fifteen"),
                LastFifteenFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Favorites"),
                FavoritesFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Request"),
                RequestFragment.class, null);



        if (savedInstanceState != null) {
        	//Use saved instance state to set currentTabe
//            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

	private void setupActionBar() {
        final com.actionbarsherlock.app.ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false); 
        getSupportActionBar().setDisplayShowHomeEnabled(false);
		
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
