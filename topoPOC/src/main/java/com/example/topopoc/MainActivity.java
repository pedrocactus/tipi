package com.example.topopoc;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private MapFragment mapFragment;
    private VoieSliderFragment voieFragment;
    private CharSequence mTitle;
    private boolean isDrawerLocked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.root_layout);

		mPlanetTitles = getResources().getStringArray(R.array.planets);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
          /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.sand)));


        // Set the adapter for the list view
		 mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		 android.R.layout.simple_list_item_1, mPlanetTitles));
		 // Set the list's click listener
		 mDrawerList.setOnItemClickListener(new OnItemClickListener() {
		
		 @Override
		 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		 long arg3) {
				// Check that the activity is using the layout version with
				// the fragment_container FrameLayout
				if (findViewById(R.id.content_frame) != null) {

                    mapFragment.filerStyle(mPlanetTitles[arg2]);


				}

             mDrawerLayout.closeDrawer(mDrawerList);

			}
		});

		// Check that the activity is using the layout version with
		// the fragment_container FrameLayout
		if (findViewById(R.id.content_frame) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}

			// Create a new Fragment to be placed in the activity layout

			mapFragment = new MapFragment();

			// In case this activity was started with special instructions from
			// an
			// Intent, pass the Intent's extras to the fragment as arguments
			mapFragment.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content_frame, mapFragment).commit();
		}
        voieFragment = new VoieSliderFragment("6A");
	}



    private void showFragment(final String fragmentTag) {
        if (fragmentTag == null||fragmentTag.isEmpty())
            return;
        // Begin a fragment transaction.
        final FragmentManager fm = getSupportFragmentManager();

        final FragmentTransaction ft = fm.beginTransaction();


        if(fragmentTag.equals(MapFragment.TAG)) {

            // We can also animate the changing of fragment.
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right,R.anim.slide_out_left);
            mapFragment = (MapFragment) fm.findFragmentByTag(MapFragment.TAG);
            if(mapFragment==null) {
                mapFragment = new MapFragment();
                // Replace current fragment by the new one.
                ft.add(R.id.content_frame, mapFragment);

            }else{
                // Replace current fragment by the new one.
                ft.replace(R.id.content_frame, mapFragment);

            }

        }else if(fragmentTag.equals(VoieSliderFragment.TAG)) {

            // We can also animate the changing of fragment.
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

            if (voieFragment == null) {
                voieFragment = new VoieSliderFragment("6A");
            }
             // Replace current fragment by the new one.
             ft.replace(R.id.content_frame, voieFragment);



        }

        // Null on the back stack to return on the previous fragment when user
        // press on back button.
        ft.addToBackStack(null);

        // Commit changes.
        ft.commit();
    }


    public void goToVoieFragment(View v) {
        showFragment(this.voieFragment.TAG);
    }

    public void goToMapFragment(View v) {
        showFragment(mapFragment.TAG);
    }

    private String getCurrentFragmentName() {

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.i("INFOO",": "+backStackEntryCount);

        String fragmentName;

        if (backStackEntryCount > 0) {
            fragmentName = getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 1).getName();
        } else {
            fragmentName = "";
        }

        return fragmentName;
    }

/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_BACK && getCurrentFragmentName().equals("VoieSliderFragment")) {
            showFragment(this.mapFragment.TAG);
            return true;

        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
*/
@Override
public void setTitle(CharSequence title) {
    mTitle = title;
    //getActionBar().setTitle(mTitle);
}
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super. onBackPressed();
                return true;
        }

                return super.onOptionsItemSelected(item);
       }

    public void setDrawerEnable(boolean enabled){
    if(enabled) {
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }else {
        mDrawerToggle.setDrawerIndicatorEnabled(false);
    }

    }
}
