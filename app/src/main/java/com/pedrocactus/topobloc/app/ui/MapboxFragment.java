package com.pedrocactus.topobloc.app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.api.ILatLng;
import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.tileprovider.tilesource.ITileLayer;
import com.mapbox.mapboxsdk.tileprovider.tilesource.MapboxTileLayer;
import com.path.android.jobqueue.JobManager;
import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.BusProvider;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.job.NationalSiteJob;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.ui.utils.SiteStyler;
import com.pedrocactus.topobloc.app.ui.utils.VoieBulle;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlFolder;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.Style;
import org.osmdroid.bonuspack.overlays.FolderOverlay;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import com.mapbox.mapboxsdk.overlay.ItemizedIconOverlay;
import com.mapbox.mapboxsdk.overlay.ItemizedOverlay;
import com.mapbox.mapboxsdk.overlay.Overlay;

import com.mapbox.mapboxsdk.views.MapView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

public class MapboxFragment extends BaseFragment implements MapListener{

    public static String TAG = "MapboxFragment";


	private ItemizedOverlay mMyLocationOverlay;
	private DefaultResourceProxyImpl mResourceProxy;
    private MapTileProviderArray provider;
    private IArchiveFile[] files;
    private MapView mapView;
    private  KmlFeature.Styler normalStyler;
    private  KmlFeature.Styler pointedStyler;
    private String currentMap;

    private List<Place> places;


    @Inject
    JobManager jobManager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.mapbox, container, false);
        TopoblocApp.injectMembers(this);
        ((MainActivity)getActivity()).setDrawerEnable(true);
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.setMinZoomLevel(mapView.getTileProvider().getMinimumZoomLevel());
        mapView.setMaxZoomLevel(mapView.getTileProvider().getMaximumZoomLevel());
        mapView.setCenter(mapView.getTileProvider().getCenterCoordinate());
        mapView.setZoom(0);

        currentMap = getString(R.string.streetMapId);
        mapView.setUserLocationEnabled(true);


		return rootView;
	}



    private void fetchNationalSites(){
        jobManager.addJobInBackground(new NationalSiteJob());
    }
    public void onEventMainThread(FetchPlacesEvent event) {
        places =  event.getPlaces();
        showFeatures(places);
    }

    private void showFeatures(List<Place> places){
        final ArrayList<Marker> items = new ArrayList<Marker>();
        GeoPoint point3 = new GeoPoint(48.6590, -4.3905);

        for (int i=0;i<places.size();i++){
            // Put overlay icon a little way from map centre
            mapView.addMarker(new Marker("Here", "SampleDescription", new LatLng(places.get(i).getCoordinates()[1], places.get(i).getCoordinates()[0])));

        }
        //items.add(new OverlayItem("Here", "SampleDescription", point3));

		/* OnTapListener for the Markers, shows a simple Toast. */


//        this.mMyLocationOverlay = new ItemizedIconOverlay(items,
//                new ItemizedIconOverlay.OnItemGestureListener<Overlay>() {
//                    @Override
//                    public boolean onItemSingleTapUp(final int index,
//                                                     final Overlay item) {
//                        Intent intent = new Intent(getActivity(), PanoramaActivity.class);
//                        startActivity(intent);
//                        return true; // We 'handled' this event.
//                    }
//
//                    @Override
//                    public boolean onItemLongPress(final int index,
//                                                   final Overlay item) {
////                        Toast.makeText(getActivity(),
////                                "Item '" + item.getTitle(), Toast.LENGTH_LONG)
////                                .show();
//                        return false;
//                    }
//                }, mResourceProxy);

        ItemizedOverlay overlayTemp = new ItemizedOverlay() {
            @Override
            protected Marker createItem(int i) {
                return items.get(i);
            }

            @Override
            public int size() {
                return items.size();
            }

            @Override
            public boolean onSnapToItem(int x, int y, Point snapPoint, MapView mapView) {

                ((MainActivity)getActivity()).showPanelDescription();
                return false;
            }
        };
//        mapView.addItemizedOverlay(this.mMyLocationOverlay);

       // mapView.addItemizedOverlay(overlayTemp);

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

        provider.detach();
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
