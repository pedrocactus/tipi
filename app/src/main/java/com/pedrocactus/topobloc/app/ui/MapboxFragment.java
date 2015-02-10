package com.pedrocactus.topobloc.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.tileprovider.tilesource.ITileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.BusProvider;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.events.SwipeDetailEvent;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.job.NationalSitesJob;
import com.pedrocactus.topobloc.app.job.RoutesJob;
import com.pedrocactus.topobloc.app.job.SectorsJob;
import com.pedrocactus.topobloc.app.job.SitesJob;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.model.Site;
import com.pedrocactus.topobloc.app.ui.base.BaseFragment;
import com.squareup.otto.Subscribe;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.util.GeoPoint;
import com.mapbox.mapboxsdk.overlay.ItemizedIconOverlay;
import com.mapbox.mapboxsdk.overlay.ItemizedOverlay;

import com.mapbox.mapboxsdk.views.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapboxFragment extends BaseFragment implements MapListener{

    public static String TAG = "MapboxFragment";


	private ItemizedOverlay mMyLocationOverlay;
	private DefaultResourceProxyImpl mResourceProxy;
    private IArchiveFile[] files;
    private MapView mapView;
    private  KmlFeature.Styler normalStyler;
    private  KmlFeature.Styler pointedStyler;
    private String currentMap;

    private List<Place> places;
    private List<Job> previousJobs;



    @Inject
    JobManager jobManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.mapbox, container, false);
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.setMinZoomLevel(mapView.getTileProvider().getMinimumZoomLevel());
        mapView.setMaxZoomLevel(24);
        mapView.setCenter(new LatLng(46.629824,1.845703));
        mapView.setZoom(7);
        mapView.setDiskCacheEnabled(true);

        currentMap = getString(R.string.outdoorsMapId);
        previousJobs


		return rootView;
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
        showFeatures(places);
    }

    public void onEventMainThread(ZoomToEvent event) {

        LatLng northEast = new LatLng(event.getBoundingBox()[5],event.getBoundingBox()[4]);
        LatLng southWest = new LatLng(event.getBoundingBox()[1],event.getBoundingBox()[0]);
        mapView.zoomToBoundingBox(new BoundingBox(northEast,southWest));

        if(places.get(0) instanceof NationalSite){
            fetchSites(event.getNamePoint());
        }else if(places.get(0) instanceof Site){
            fetchSectors(event.getNamePoint());
        }else if(places.get(0) instanceof Sector){
            fetchRoutes(event.getNamePoint());
        }
        mapView.removeOverlay(mMyLocationOverlay);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(oldPlace.getName());


    }

    public void onEventMainThread(SwipeDetailEvent event) {
        mMyLocationOverlay.getItem(event.getIndexToShow()).setIcon(new Icon(getResources().getDrawable(R.drawable.defpin)));

        if(places.get(0) instanceof Route) {
            mMyLocationOverlay.getItem(event.getPreviousIndex()).setIcon(new Icon(getResources().getDrawable(R.drawable.ic_brightness_1_black_18dp)));
        }else{
            mMyLocationOverlay.getItem(event.getPreviousIndex()).setIcon(new Icon(getResources().getDrawable(R.drawable.ic_terrain_black_48dp)));
        }
        mapView.invalidate();

    }

//    public void onEventMainThread(ShowDetailEvent event) {
//        mMyLocationOverlay.getItem(event.getIndexToShow()).setIcon(new Icon(getResources().getDrawable(R.drawable.defpin)));
//        mapView.invalidate();
//
//    }

    private void showFeatures(final List<Place> places){


//        if(places.get(0) instanceof Route) {
//            TextOverlay textOverlay = new TextOverlay(getActivity(),mapView,(List<Route>)(List<?>)places, new TextOverlay.OnItemGestureListener<Route>(){
//                @Override
//                public boolean onItemSingleTapUp(int i,Route route) {
//                    ((MainActivity) getActivity()).showPanelDescription(i);
//                    mMyLocationOverlay.getItem(i).setIcon(new Icon(getResources().getDrawable(R.drawable.defpin)));
//                    mapView.invalidate();
//
//                    return true;
//                }
//
//                @Override
//                public boolean onItemLongPress(int i,Route route) {
//                    return true;
//                }
//            });
//            mapView.addOverlay(textOverlay);
//        }else {


            final ArrayList<Marker> items = new ArrayList<Marker>();
            GeoPoint point3 = new GeoPoint(48.6590, -4.3905);
            ArrayList<Marker> markers = new ArrayList<Marker>();
            for (int i = 0; i < places.size(); i++) {
                // Put overlay icon a little way from map centre
                Marker marker = new Marker("Here", "SampleDescription", new LatLng(places.get(i).getCoordinates()[1], places.get(i).getCoordinates()[0]));
                if(places.get(0) instanceof Route) {
                    marker.setIcon(new Icon(getResources().getDrawable(R.drawable.ic_brightness_1_black_18dp)));
                }else{
                    marker.setIcon(new Icon(getResources().getDrawable(R.drawable.ic_terrain_black_48dp)));
                }
                markers.add(marker);

            }

            mMyLocationOverlay = new ItemizedIconOverlay(getActivity(), markers, new ItemizedIconOverlay.OnItemGestureListener<Marker>() {
                @Override
                public boolean onItemSingleTapUp(int i, Marker marker) {
                    ((MainActivity) getActivity()).showPanelDescription(i);
                    mMyLocationOverlay.getItem(i).setIcon(new Icon(getResources().getDrawable(R.drawable.defpin)));
                    oldPlace.add(places.get(i));
                    mapView.invalidate();

                    return true;
                }

                @Override
                public boolean onItemLongPress(int i, Marker marker) {
                    Toast.makeText(getActivity(), "Marker Selected: " + marker.getTitle(), Toast.LENGTH_LONG).show();
                    return true;
                }
            });
            mapView.addItemizedOverlay(this.mMyLocationOverlay);

//        }

        mapView.invalidate();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null){
            //routes = savedInstanceState.getParcelableArrayList("routes");
            //showFeatures(sector.getRoutes());
        }else {
            fetchNationalSites();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList("routes",routes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView(){

        mapView.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Subscribe
    public void zoomTo(ZoomToEvent event){


    }


    @Override
    public boolean onScroll(ScrollEvent scrollEvent) {
        return false;
    }

    @Override
    public boolean onZoom(ZoomEvent zoomEvent) {
//        mapView.getBoundingBox().getLatitudeSpan()>oldPlace.getBoundingbox()getBoundingbox();

        if(mapView.getZoomLevel()<zoomEvent.getZoomLevel()){

        }

        return false;
    }

    private File createFileFromInputStream(InputStream inputStream) {

        try{
            File f = new File("sitesPoint.geojson");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    protected void replaceMapView(String layer) {
        if (TextUtils.isEmpty(layer) || TextUtils.isEmpty(currentMap) || currentMap.equalsIgnoreCase(layer)) {
            return;
        }
        ITileLayer source;
        BoundingBox box;
        source = new MapboxTileLayer(layer);
        mapView.setTileSource(source);
        box = source.getBoundingBox();
        mapView.setScrollableAreaLimit(box);
        mapView.setMinZoomLevel(mapView.getTileProvider().getMinimumZoomLevel());
        mapView.setMaxZoomLevel(mapView.getTileProvider().getMaximumZoomLevel());
        currentMap = layer;
/*
mv.setCenter(mv.getTileProvider().getCenterCoordinate());
mv.setZoom(0);
*/
    }
    private Button changeButtonTypeface(Button button) {
        return button;
    }
    public LatLng getMapCenter() {
        return mapView.getCenter();
    }
    public void setMapCenter(ILatLng center) {
        mapView.setCenter(center);
    }
}
