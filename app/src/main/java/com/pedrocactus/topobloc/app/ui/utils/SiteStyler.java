package com.pedrocactus.topobloc.app.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.pedrocactus.topobloc.app.ui.MainActivity;
import com.pedrocactus.topobloc.app.R;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.kml.Style;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

/**
 * Created by pierrecastex on 24/03/2014.
 */
public class SiteStyler implements KmlFeature.Styler {
    private Drawable mMarker;
    private MapView mapView;
    private Style normalStyle;
    private Style defaultStyle;
    private KmlDocument docu;

    private Drawable defaultMarker;
    public SiteStyler(Drawable marker, MapView map, Context context, KmlDocument document){
        mapView = map;
        mMarker = marker;
        defaultMarker = context.getResources().getDrawable(R.drawable.marker);
        Bitmap defaultBitmap = ((BitmapDrawable)defaultMarker).getBitmap();
        defaultStyle = new Style(defaultBitmap, 0x901010AA, 3.0f, 0x20AA1010);


        Drawable normalMarker = context.getResources().getDrawable(R.drawable.marker_node);
        Bitmap normalBitmap = ((BitmapDrawable)normalMarker).getBitmap();
        normalStyle = new Style(defaultBitmap, 0x901010AA, 3.0f, 0x20AA1010);
        docu = document;
    }


    @Override
    public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

    }

    @Override
    public void onPoint(Marker marker, final KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        marker.setTitle(kmlPlacemark.mExtendedData.get("nom"));
        //marker.setRelatedObject();
        Drawable normalMarker = mapView.getContext().getResources().getDrawable(R.drawable.marker_trans);
        marker.setIcon(normalMarker);
        //marker.setInfoWindow(new SiteInfoWindow(mapView));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                marker.setIcon(defaultMarker);
                ((MainActivity)mapView.getContext()).showPanelDescription(kmlPlacemark);
                Toast.makeText(mapView.getContext(),"yes",Toast.LENGTH_SHORT).show();
                mapView.invalidate();
                return false;
            }
        });

    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

    }

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

    }
}
