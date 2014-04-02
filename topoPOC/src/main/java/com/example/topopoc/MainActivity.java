package com.example.topopoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
    MapFragment firstFragment;
    VoieFragment voieFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.root_layout);

		mPlanetTitles = getResources().getStringArray(R.array.planets);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

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

                    firstFragment.filerStyle(mPlanetTitles[arg2]);


				}

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

			firstFragment = new MapFragment();

			// In case this activity was started with special instructions from
			// an
			// Intent, pass the Intent's extras to the fragment as arguments
			firstFragment.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content_frame, firstFragment).commit();
		}
        voieFragment = new VoieFragment("6A");
	}

    private void showFragment(final Fragment fragment) {
        if (fragment == null)
            return;

        // Begin a fragment transaction.
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        // We can also animate the changing of fragment.
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        // Replace current fragment by the new one.
        ft.replace(R.id.content_frame, fragment);
        // Null on the back stack to return on the previous fragment when user
        // press on back button.
        ft.addToBackStack(null);

        // Commit changes.
        ft.commit();
    }

    public void goToVoieFragment(View v) {
        showFragment(this.voieFragment);
    }

}
