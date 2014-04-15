package com.example.topopoc;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.reflect.Field;

/**
 * Created by pierrecastex on 02/04/2014.
 */
public class VoieSliderFragment extends Fragment {




    /****************************FIX FOR NESTED FRAGMENTS, MAYBE WILL BE FIXED IN NEXT RELEASE OF COMPAT LIB ANDROID******************************/





    private static final Field sChildFragmentManagerField;

    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.e("ERROR MESSAGE", "Error getting mChildFragmentManager field", e);
        }
        sChildFragmentManagerField = f;
    }



/*************************COMMON VARIABLES***************************************/



    public static String TAG = "VoieSliderFragment";
    private String voie;

    /********************VIEWPAGER STUFF*****************************************/
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    private VoieFragment voieFragment;



    DisplayImageOptions options;



    public VoieSliderFragment(String voie){
        this.voie = voie;
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setDrawerEnable(false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.voie_slider_layout, container, false);
        ///A corriger flemme de mettre des images
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.marker)
                .showImageOnFail(R.drawable.moreinfo_arrow)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        mPager = (ViewPager) view.findViewById(R.id.voie_slider_viewpager);
        mPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
        //mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        new SetAdapterTask().execute();

        return view;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
            public Fragment getItem(int position) {
                voieFragment = new VoieFragment(position);
                return voieFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            //do nothing here! no call to super.restoreState(arg0, arg1);
        }
    }

    private class SetAdapterTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(mPagerAdapter != null) mPager.setAdapter(mPagerAdapter);
        }
    }

    @Override
    public void onDestroyView() {

        try{
            FragmentTransaction transaction = getChildFragmentManager()
                    .beginTransaction();

            transaction.remove(voieFragment);

            transaction.commit();
        }catch(Exception e){
        }
        mPagerAdapter = null;

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        BusProvider.getInstance().unregister(this);
        try {
            Field field = Fragment.class.getDeclaredField("mChildFragmentManager");
            field.setAccessible(true);
            field.set(this, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
    }
    @Override
    public void onPause() {
        super.onPause();
    }

}
