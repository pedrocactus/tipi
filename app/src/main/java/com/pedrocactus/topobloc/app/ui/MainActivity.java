package com.pedrocactus.topobloc.app.ui;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.ui.about.AboutFragment;
import com.pedrocactus.topobloc.app.ui.list.ListFragment;
import com.pedrocactus.topobloc.app.ui.panel.CustomDrawerAdapter;
import com.pedrocactus.topobloc.app.ui.panel.DrawerItem;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.events.PhotoTouchEvent;
import com.pedrocactus.topobloc.app.events.ShowDetailEvent;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.ui.base.BaseActivity;
import com.pedrocactus.topobloc.app.ui.slideuppanel.DetailSliderFragment;
import com.pedrocactus.topobloc.app.ui.utils.Utils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private String[] sites;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    List<DrawerItem> dataList;
    CustomDrawerAdapter adapter;
    //private MapFragment mapFragment;
    private MapboxFragment mapBoxFragment;
    private AboutFragment aboutFragment;
    private VoieSliderFragment voieFragment;
    private DetailSliderFragment detailSliderFragment;
    private ListFragment listFragment;
    private CharSequence mTitle;
    private boolean isDrawerLocked = false;
    private SlidingUpPanelLayout panelLayout;
    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";



    private List<Place> places;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);


		setContentView(R.layout.root_layout);

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
        sites = getResources().getStringArray(R.array.sites);
        dataList = new ArrayList<DrawerItem>();
//        dataList.add(new DrawerItem("Sites", R.drawable.sitemap,true));
        dataList.add(new DrawerItem(sites[0],R.drawable.ic_local_library_white_48dp,false));
        dataList.add(new DrawerItem(sites[1], R.drawable.ic_map_white_48dp,true));
        dataList.add(new DrawerItem(sites[2], R.drawable.ic_search_white_48dp,false));
        dataList.add(new DrawerItem(sites[3], R.drawable.ic_person_white_48dp,true));
        dataList.add(new DrawerItem(sites[4],R.drawable.ic_settings_applications_white_48dp ,false));
        dataList.add(new DrawerItem(sites[5],R.drawable.ic_info_white_48dp ,true));
//        dataList.add(new DrawerItem("Filtres", R.drawable.filter,true));
//        dataList.add(new DrawerItem("Niveau", DrawerItem.NO_IMAGE,false));
//        dataList.add(new DrawerItem("Style", DrawerItem.NO_IMAGE,false));
//        dataList.add(new DrawerItem("Options", R.drawable.settings,true));
        adapter = new CustomDrawerAdapter(this, R.layout.title_drawer_item_nosep,R.layout.title_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

               // BusProvider.getInstance().post(new ZoomToEvent(sites[arg2-1]));

                mDrawerLayout.closeDrawer(mDrawerList);
                if(arg2==5)
                goToAboutFragment(arg1);

                if(arg2==1)
                goToMapFragment(arg1);

                if(arg2==0)
                goToListFragment(arg1);


            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.sand)));




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

            mapBoxFragment = new MapboxFragment();

			// In case this activity was started with special instructions from
			// an
			// Intent, pass the Intent's extras to the fragment as arguments
            mapBoxFragment.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content_frame, mapBoxFragment).commit();
		}
        voieFragment = new VoieSliderFragment();


        panelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        panelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                setActionBarTranslation(panelLayout.getCurrentParalaxOffset());
                if (panel.getPaddingBottom() != 0) {
                    panel.setPadding(panel.getPaddingLeft(),
                            panel.getPaddingTop(), panel.getPaddingRight(), 0);
                }
            }

            @Override
            public void onPanelCollapsed(View view) {

            }

            @Override
            public void onPanelExpanded(View view) {

            }

            @Override
            public void onPanelAnchored(View panel) {
                int paddingPx = (int) Utils.getPixels(TypedValue.COMPLEX_UNIT_DIP, 68);
                panel.setPadding(panel.getPaddingLeft(),
                        panel.getPaddingTop(), panel.getPaddingRight(), paddingPx);

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });

        panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
	}



    private void showFragment(final String fragmentTag) {
        if (fragmentTag == null||fragmentTag.equals(""))
            return;
        // Begin a fragment transaction.
        final FragmentManager fm = getSupportFragmentManager();

        final FragmentTransaction ft = fm.beginTransaction();


        if(fragmentTag.equals(MapboxFragment.TAG)) {

            // We can also animate the changing of fragment.
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right,R.anim.slide_out_left);
            mapBoxFragment = (MapboxFragment) fm.findFragmentByTag(MapboxFragment.TAG);
            if(mapBoxFragment==null) {
                mapBoxFragment = new MapboxFragment();
                // Replace current fragment by the new one.
                ft.add(R.id.content_frame, mapBoxFragment);

            }else{
                // Replace current fragment by the new one.
                ft.replace(R.id.content_frame, mapBoxFragment);

            }

        }else if(fragmentTag.equals(VoieSliderFragment.TAG)) {

            // We can also animate the changing of fragment.
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

            if (voieFragment == null) {
                voieFragment = new VoieSliderFragment();
            }
            // Replace current fragment by the new one.
            ft.replace(R.id.content_frame, voieFragment);


        }else if(fragmentTag.equals(AboutFragment.TAG)) {

                // We can also animate the changing of fragment.
//                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

                if (aboutFragment == null) {
                    aboutFragment = new AboutFragment();
                }
                // Replace current fragment by the new one.
                ft.replace(R.id.content_frame, aboutFragment);



            }else if(fragmentTag.equals(ListFragment.TAG)) {

            // We can also animate the changing of fragment.
//                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

            if (listFragment == null) {
                listFragment = new ListFragment();
                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList("places", (ArrayList<Place>) places);
                listFragment.setArguments(arguments);
            }

            // Replace current fragment by the new one.
            ft.replace(R.id.content_frame, listFragment);



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
        showFragment(mapBoxFragment.TAG);
    }

    public void goToListFragment(View v) {
        showFragment(listFragment.TAG);
    }


    public void goToAboutFragment(View v) {
        showFragment(aboutFragment.TAG);
    }


    public void onEventMainThread(FetchPlacesEvent event) {
        places = event.getPlaces();
        detailSliderFragment = new DetailSliderFragment();
                Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("places", (ArrayList<Place>) event.getPlaces());
        detailSliderFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.panel, detailSliderFragment).commit();
    }

    public void onEventMainThread(PhotoTouchEvent event) {
        panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        final FragmentManager fm = getSupportFragmentManager();

        final FragmentTransaction ft = fm.beginTransaction();
        // We can also animate the changing of fragment.
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        if (voieFragment == null) {
            voieFragment = new VoieSliderFragment();
        }
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("places", (ArrayList<Place>) places);
        arguments.putInt("placeIndex",event.getIndex());
        voieFragment.setArguments(arguments);
        // Replace current fragment by the new one.
        ft.replace(R.id.content_frame, voieFragment);

        ft.commit();
    }

    public void showPanelDescription(int placeIndex){
//        PanelDescriptionFragment panelFragment = new PanelDescriptionFragment();
//        Bundle arguments = new Bundle();
//        arguments.putParcelable("description",feature);
//        panelFragment.setArguments(arguments);

//        DetailFragment panelFragment = new DetailFragment();
//        Bundle arguments = new Bundle();
//        arguments.putParcelable("detailPlace", place);
//        panelFragment.setArguments(arguments);

//        DetailSliderFragment slierFragment = new DetailSliderFragment();
//        Bundle arguments = new Bundle();
//        arguments.putParcelable("detailPlace", places);
//        slierFragment.setArguments(arguments);


        //if (voieFragment == null) {
        //    voieFragment = new VoieSliderFragment("6A");
        //}
        // Replace current fragment by the new one.
        //getSupportFragmentManager().beginTransaction()
          //      .add(R.id.panel, voieFragment).commit();


        eventBus.post(new ShowDetailEvent(placeIndex));
        panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);


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
                super.onBackPressed();
                return true;
        }

                return super.onOptionsItemSelected(item);
       }

    public void setDrawerEnable(boolean enabled){
        mDrawerToggle.setDrawerIndicatorEnabled(enabled);
        isDrawerLocked = !enabled;
        if(enabled) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }else{
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        }

    }
    private int getActionBarHeight(){
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public void onEventMainThread(ZoomToEvent event) {
        panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }



    public void setActionBarTranslation(float y) {
        // Figure out the actionbar height
        //
        int actionBarHeight = getActionBarHeight();
        // A hack to add the translation to the action bar
        ViewGroup content = ((ViewGroup) findViewById(android.R.id.content).getParent());
        int children = content.getChildCount();
        for (int i = 0; i < children; i++) {
            View child = content.getChildAt(i);
            if (child.getId() != android.R.id.content) {
                if (y <= -actionBarHeight) {
                    child.setVisibility(View.GONE);
                } else {
                    child.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        child.setTranslationY(y);
                    } else {
                        AnimatorProxy.wrap(child).setTranslationY(y);
                    }
                }
            }
        }
    }

    public List<Place> getPlaces(){
        return places;
    }

    public void setPlaces(List<Place> places){
        this.places = places;
    }
}
