package com.pedrocactus.topobloc.app.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.events.MapListener;
import com.mapbox.mapboxsdk.events.RotateEvent;
import com.mapbox.mapboxsdk.events.ScrollEvent;
import com.mapbox.mapboxsdk.events.ZoomEvent;
import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.ItemizedIconOverlay;
import com.mapbox.mapboxsdk.overlay.ItemizedOverlay;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.tileprovider.tilesource.ITileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.mapbox.mapboxsdk.views.MapView;
import com.path.android.jobqueue.JobManager;
import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.BusProvider;
import com.pedrocactus.topobloc.app.events.FetchAreaEvent;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.events.SwipeDetailEvent;
import com.pedrocactus.topobloc.app.events.ZoomOutEvent;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.job.NationalSiteJob;
import com.pedrocactus.topobloc.app.job.NationalSitesJob;
import com.pedrocactus.topobloc.app.job.RoutesJob;
import com.pedrocactus.topobloc.app.job.SectorJob;
import com.pedrocactus.topobloc.app.job.SectorsJob;
import com.pedrocactus.topobloc.app.job.SiteJob;
import com.pedrocactus.topobloc.app.job.SitesJob;
import com.pedrocactus.topobloc.app.model.Area;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.model.Site;
import com.pedrocactus.topobloc.app.ui.base.BaseFragment;
import com.pedrocactus.topobloc.app.ui.utils.Utils;
import com.pedrocactus.topobloc.app.ui.widget.RouteIconWidget;
import com.squareup.otto.Subscribe;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.util.GeoPoint;

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
    private int selectedIndex;

    private Area area;
    private float zLevelLimit;
    private float actualZLevelLimit;
    private BoundingBox lastBoundingBox;



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
        mapView.addListener(this);
        mapView.setUserLocationEnabled(true);

        lastBoundingBox = mapView.getBoundingBox();
        currentMap = getString(R.string.outdoorsMapId);
        if(savedInstanceState!=null){
            mapView.zoomToBoundingBox((BoundingBox)savedInstanceState.getParcelable("boundingbox"));
        }

		return rootView;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null){
            area = savedInstanceState.getParcelable("area");
            mapView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    return false;
                }
            });
            showFeatures(area.getPlaces());
        }else {

            fetchNationalSites();
        }
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



    private void fetchNationalSite(String nationaSiteName){
        jobManager.addJobInBackground(new NationalSiteJob(nationaSiteName));
    }

    private void fetchSite(String siteName){
        jobManager.addJobInBackground(new SiteJob(siteName));
    }

    private void fetchSector(String sectorName){
        jobManager.addJobInBackground(new SectorJob(sectorName));
    }


    public void onEventMainThread(FetchPlacesEvent event) {
        places =  (ArrayList<Place>)event.getPlaces();
        showFeatures(places);
    }

    public void onEventMainThread(FetchAreaEvent event) {
        area  =  event.getArea();
        places = area.getPlaces();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(area.getName());
        showFeatures(area.getPlaces());
    }

    public void onEventMainThread(ZoomToEvent event) {
        selectedIndex = -1;
        lastBoundingBox = mapView.getBoundingBox();



        mapView.zoomToBoundingBox(Utils.getBoundingBox(event.getBoundingBox()));
        zLevelLimit = mapView.getZoomLevel();

//        if(places.get(0) instanceof NationalSite){
//            fetchSites(event.getNamePoint());
//        }else if(places.get(0) instanceof Site){
//            fetchSectors(event.getNamePoint());
//        }else if(places.get(0) instanceof Sector){
//            fetchRoutes(event.getNamePoint());
//        }
        if(area == null){
            fetchNationalSite(event.getNamePoint());
        }else if(area instanceof NationalSite){
            fetchSite(event.getNamePoint());
        }else if(area instanceof Site){
            fetchSector(event.getNamePoint());
        }

        mapView.removeOverlay(mMyLocationOverlay);

        if(area!=null)
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(area.getName());


    }

    public void onEventMainThread(ZoomOutEvent event) {
        LatLng northEast = new LatLng(event.getBoundingBox()[5],event.getBoundingBox()[4]);
        LatLng southWest = new LatLng(event.getBoundingBox()[1],event.getBoundingBox()[0]);
        mapView.zoomToBoundingBox(new BoundingBox(northEast,southWest));

        zLevelLimit = mapView.getZoomLevel();

        if(area instanceof NationalSite){
            fetchNationalSites();
        }else if(area instanceof Sector){
            fetchSite(event.getNamePoint());
        }if(area instanceof Site){
            fetchNationalSite(event.getNamePoint());
        }

        mapView.removeOverlay(mMyLocationOverlay);


    }

    public void onEventMainThread(SwipeDetailEvent event) {

        if(places.get(0) instanceof Route) {
            Icon.Size sizeIcon;
            int size = 0;
            if(actualZLevelLimit>20){

                sizeIcon =  Icon.Size.LARGE;
                size = RouteIconWidget.BIG;
            }else{
                sizeIcon =  Icon.Size.SMALL;
                size =  RouteIconWidget.SMALL;
            }
            mMyLocationOverlay.getItem(event.getIndexToShow()).setIcon(new Icon(getActivity(), sizeIcon, String.valueOf(((Route) places.get(event.getIndexToShow())).getNumber()), "FF0000"));

            RouteIconWidget iconWidget = new RouteIconWidget(getActivity(), String.valueOf(((Route)places.get(event.getPreviousIndex())).getNumber()), R.color.red, size);
            iconWidget.setDrawingCacheEnabled(true);

            iconWidget.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            iconWidget.layout(0, 0, iconWidget.getMeasuredWidth(), iconWidget.getMeasuredHeight());

            iconWidget.buildDrawingCache(true);
            mMyLocationOverlay.getItem(event.getPreviousIndex()).setMarker(new BitmapDrawable(getResources(), Bitmap.createBitmap(iconWidget.getDrawingCache())));
            mMyLocationOverlay.setFocus(mMyLocationOverlay.getItem(event.getIndexToShow()));
            iconWidget.setDrawingCacheEnabled(false);
            if(!mapView.getBoundingBox().contains(mMyLocationOverlay.getItem(event.getIndexToShow()).getPoint())){
                mapView.getController().animateTo(new LatLng(places.get(event.getIndexToShow()).getCoordinates()[1], places.get(event.getIndexToShow()).getCoordinates()[0]));
            }
        }else{
            mMyLocationOverlay.getItem(event.getIndexToShow()).setIcon(new Icon(getResources().getDrawable(R.drawable.defpin)));
            mMyLocationOverlay.getItem(event.getPreviousIndex()).setMarker(getResources().getDrawable(R.drawable.ic_terrain_black_48dp));
        }

        selectedIndex = event.getIndexToShow();

        mapView.invalidate();

    }


    private void showFeatures(final List<Place> places){



            final ArrayList<Marker> items = new ArrayList<Marker>();
            GeoPoint point3 = new GeoPoint(48.6590, -4.3905 );
            ArrayList<Marker> markers = new ArrayList<Marker>();
            for (int i = 0; i < places.size(); i++) {
                // Put overlay icon a little way from map centre
                Marker marker = new Marker("Here", "SampleDescription", new LatLng(places.get(i).getCoordinates()[1], places.get(i).getCoordinates()[0]));
                Place place = places.get(i);

                if(places.get(0) instanceof Route) {
                    Route route = (Route) places.get(i);

                    String name = "ic brightness 1 "+ ((Route) place).getCircuit()+" 24dp";
                    int draw = getActivity().getResources().getIdentifier(name.toLowerCase().replace(" ", "_"),"drawable",getActivity().getPackageName());
                    Icon icon = new Icon(getActivity(), Icon.Size.SMALL, String.valueOf(route.getNumber()), "FF0000");
                    int size =0;
                    Icon.Size sizeIcon;
                    if(mapView.getZoomLevel()>20){

                        size = RouteIconWidget.BIG;
                        sizeIcon = Icon.Size.LARGE;
                    }else{

                        size = RouteIconWidget.SMALL;
                        sizeIcon = Icon.Size.SMALL;
                    }
                    if((selectedIndex!=-1)&&(selectedIndex==i)){
                        marker.setIcon(new Icon(getActivity(), sizeIcon, String.valueOf(((Route)places.get(selectedIndex)).getNumber()), "FF0000"));
                    }else {
                        RouteIconWidget iconWidget = new RouteIconWidget(getActivity(), String.valueOf(route.getNumber()), R.color.red, size);
                        iconWidget.setDrawingCacheEnabled(true);

                        iconWidget.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                        iconWidget.layout(0, 0, iconWidget.getMeasuredWidth(), iconWidget.getMeasuredHeight());

                        iconWidget.buildDrawingCache(true);
                        marker.setMarker(new BitmapDrawable(getResources(), Bitmap.createBitmap(iconWidget.getDrawingCache())));
                        iconWidget.setDrawingCacheEnabled(false);
                    }
                }else{
                    marker.setMarker(getResources().getDrawable(R.drawable.ic_terrain_black_48dp));
                }
                    markers.add(marker);

            }

            mMyLocationOverlay = new ItemizedIconOverlay(getActivity(), markers, new ItemizedIconOverlay.OnItemGestureListener<Marker>() {
                @Override
                public boolean onItemSingleTapUp(int i, Marker marker) {
                    selectedIndex = i;
                    ((MainActivity) getActivity()).showPanelDescription(i);
                    if(places.get(i) instanceof Route) {
                        Icon.Size size;
                        if (actualZLevelLimit > 20) {

                            size = Icon.Size.LARGE;
                        } else {
                            size = Icon.Size.SMALL;
                        }
                        mMyLocationOverlay.getItem(i).setIcon(new Icon(getActivity(),size, String.valueOf(((Route) places.get(i)).getNumber()), "FF0000"));
                    }else {
                        mMyLocationOverlay.getItem(i).setIcon(new Icon(getResources().getDrawable(R.drawable.defpin)));
                    }
                    mapView.invalidate();

                    return true;
                }

                @Override
                public boolean onItemLongPress(int i, Marker marker) {
                    return true;
                }
            });
            if(selectedIndex!=-1){
                mMyLocationOverlay.setFocus(mMyLocationOverlay.getItem(selectedIndex));
            }
            mapView.addItemizedOverlay(this.mMyLocationOverlay);


        mapView.invalidate();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("area",area);
        outState.putParcelable("boundingbox", mapView.getBoundingBox());
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

//        TopoblocApp.getInstance().saveStringInPreferences(mapView.getBoundingBox(),"boundingbox");
    }


    @Subscribe
    public void zoomTo(ZoomToEvent event){


    }


    @Override
    public void onScroll(ScrollEvent scrollEvent) {

    }

    @Override
    public void onZoom(ZoomEvent zoomEvent) {
//        mapView.getBoundingBox().getLatitudeSpan()>oldPlace.getBoundingbox()getBoundingbox();

        if(zLevelLimit>zoomEvent.getZoomLevel()&&zLevelLimit!=-1){
            zLevelLimit = -1;
            eventBus.post(new ZoomOutEvent(area.getAncestors().get(0),area.getParentboundingbox()));
        }else if(places.get(0) instanceof Route){
            float nextZLevel = zoomEvent.getZoomLevel();
            if ((actualZLevelLimit<=20&&nextZLevel>20)||(nextZLevel<=20&&actualZLevelLimit>20)){
                mapView.removeOverlay(mMyLocationOverlay);
                showFeatures(places);
            }
        }
        actualZLevelLimit = zoomEvent.getZoomLevel();
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

    @Override
    public void onRotate(RotateEvent rotateEvent) {

    }
}
