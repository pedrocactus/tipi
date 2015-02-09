package com.pedrocactus.topobloc.app.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.path.android.jobqueue.JobManager;
import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.job.NationalSitesJob;
import com.pedrocactus.topobloc.app.job.RoutesJob;
import com.pedrocactus.topobloc.app.job.SectorsJob;
import com.pedrocactus.topobloc.app.job.SitesJob;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.model.Site;
import com.pedrocactus.topobloc.app.ui.about.AboutListAdapter;
import com.pedrocactus.topobloc.app.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by pierrecastex on 19/01/2015.
 */
public class ListFragment  extends BaseFragment {


    public static String TAG = "ListFragment";


    @InjectView(R.id.place_listview)
    ListView placeListView;


    private PlaceListAdapter adapter;


    private List<Place> places;

    @Inject
    JobManager jobManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.inject(this, view);


        adapter = new PlaceListAdapter(getActivity());
        placeListView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        places = getArguments().getParcelableArrayList("places");
        updateList(places);

    }

    private void fetchNationalSites(){
        jobManager.addJobInBackground(new NationalSitesJob());
    }

    private void fetchSites(String nationaSiteName){
        jobManager.addJobInBackground(new SitesJob(nationaSiteName));
    }

    private void fetchSectors(String siteName){
        jobManager.addJobInBackground(new SectorsJob(siteName));
    }

    private void fetchRoutes(String sectorName){
        jobManager.addJobInBackground(new RoutesJob(sectorName));
    }

    public void onEventMainThread(FetchPlacesEvent event) {
        places =  event.getPlaces();
        updateList(places);
    }


    private void updateList(List<Place> places){

        ((PlaceListAdapter)placeListView.getAdapter()).updatePlaces(places);
    }

    @OnItemClick(R.id.place_listview)
    public void onMovieClicked(int position){
        if(places.get(0) instanceof NationalSite){
            fetchSites(places.get(position).getName());
        }else if(places.get(0) instanceof Site){
            fetchSectors(places.get(position).getName());
        }else if(places.get(0) instanceof Sector){
            fetchRoutes(places.get(position).getName());
        }
    }

    private HashMap<String, Object> getDetailItemInfo(AboutListAdapter.AboutType type, String title, Object body) {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put(AboutListAdapter.TYPE, type);
        item.put(AboutListAdapter.TITLE, title);
        item.put(AboutListAdapter.BODY, body);
        return item;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
