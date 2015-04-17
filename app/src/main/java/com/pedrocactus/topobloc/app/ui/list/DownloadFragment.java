package com.pedrocactus.topobloc.app.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.CoordinateRegion;
import com.mapbox.mapboxsdk.geometry.CoordinateSpan;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.offline.OfflineMapDatabase;
import com.mapbox.mapboxsdk.offline.OfflineMapDownloader;
import com.mapbox.mapboxsdk.overlay.OfflineMapTileProvider;
import com.mapbox.mapboxsdk.overlay.TilesOverlay;
import com.path.android.jobqueue.JobManager;
import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.FetchAreaEvent;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.job.NationalSitesJob;
import com.pedrocactus.topobloc.app.job.RoutesJob;
import com.pedrocactus.topobloc.app.job.SectorsJob;
import com.pedrocactus.topobloc.app.job.SitesJob;
import com.pedrocactus.topobloc.app.model.Area;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.model.Site;
import com.pedrocactus.topobloc.app.ui.MainActivity;
import com.pedrocactus.topobloc.app.ui.about.AboutListAdapter;
import com.pedrocactus.topobloc.app.ui.base.BaseFragment;
import com.pedrocactus.topobloc.app.ui.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by pierrecastex on 19/01/2015.
 */
public class DownloadFragment  extends BaseFragment {


    public static String TAG = "ListFragment";


   /* @InjectView(R.id.place_listview)
    ListView placeListView;*/

    @InjectView(R.id.mSiteDownloadTextView)
    TextView mSiteDownloadTextView;

    private Area area;

    private PlaceListAdapter adapter;


    private List<Place> places;

    @Inject
    JobManager jobManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_fragment_layout, container, false);
        ButterKnife.inject(this, view);
        Button saveMapButton = (Button) view.findViewById(R.id.saveMapButton);
        saveMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveMapButton(v);
            }
        });

        Button loadMapButton = (Button) view.findViewById(R.id.loadMapButton);
        loadMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoadMapButton(v);
            }
        });

        Button deleteMapButton = (Button) view.findViewById(R.id.deleteMapsButton);
        deleteMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDeleteMapButton(v);
            }
        });

        Button resetButton = (Button) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResetButton(v);
            }
        });


        fetchSectors("Les Trois Pignons");
       /* adapter = new PlaceListAdapter(getActivity());
        placeListView.setAdapter(adapter);*/
        return view;

    }

    private void fetchDownLoadableSites(){
        jobManager.addJobInBackground(new NationalSitesJob());
    }


    public void handleSaveMapButton(View view) {
        Log.i(TAG, "handleSaveMapButton() called");
        OfflineMapDownloader offlineMapDownloader = OfflineMapDownloader.getOfflineMapDownloader(getActivity());
        BoundingBox boundingBox = Utils.getBoundingBox(area.getBoundingbox());

        CoordinateSpan span = new CoordinateSpan(boundingBox.getLatitudeSpan(), boundingBox.getLongitudeSpan());
        LatLng lat = new LatLng()
        CoordinateRegion coordinateRegion = new CoordinateRegion(area.getCoordinates(), span);
        offlineMapDownloader.beginDownloadingMapID(getString(R.string.pedroId), coordinateRegion, 17, 24);
    }

    public void handleLoadMapButton(View view) {
        Log.i(TAG, "handleLoadMapButton()");
        OfflineMapDownloader offlineMapDownloader = OfflineMapDownloader.getOfflineMapDownloader(getActivity());

        ArrayList<OfflineMapDatabase> offlineMapDatabases = offlineMapDownloader.getMutableOfflineMapDatabases();
        if (offlineMapDatabases != null && offlineMapDatabases.size() > 0) {
            OfflineMapDatabase db = offlineMapDatabases.get(0);
            Toast.makeText(getActivity(), String.format(MAPBOX_LOCALE, "Will load MapID = '%s'", db.getMapID()), Toast.LENGTH_SHORT).show();

            OfflineMapTileProvider tp = new OfflineMapTileProvider(getActivity(), db);
            offlineMapOverlay = new TilesOverlay(tp);
            mapView.addOverlay(offlineMapOverlay);
        } else {
            Toast.makeText(getActivity(), "No Offline Maps available.", Toast.LENGTH_LONG).show();
        }
    }

    public void handleDeleteMapButton(View view) {
        Log.i(TAG, "handleDeleteMapButton()");
        OfflineMapDownloader offlineMapDownloader = OfflineMapDownloader.getOfflineMapDownloader(getActivity());
        if (offlineMapDownloader.isMapIdAlreadyAnOfflineMapDatabase(getString(R.string.mapbox_id_street))) {
            boolean result = offlineMapDownloader.removeOfflineMapDatabaseWithID(getString(R.string.mapbox_id_street));
            Toast.makeText(getActivity(), String.format(MAPBOX_LOCALE, "Result of deletion attempt: '%s'", result), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "It's not an offline database yet.", Toast.LENGTH_LONG).show();
        }
    }

    public void handleResetButton(View view) {
        Log.i(TAG, "handleResetButton()");

        mapView.removeOverlay(offlineMapOverlay);
        mapView.setCenter(new LatLng(29.94423, -90.09201));
        mapView.setZoom(12);
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

    public void onEventMainThread(FetchAreaEvent event) {
        area  =  event.getArea();
        mSiteDownloadTextView.setText(area.getName());
    }


 /*   private void updateList(List<Place> places){

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
    }*/
}
