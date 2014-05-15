package com.example.topopoc.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.topopoc.R;

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
    public SiteStyler(Drawable marker, MapView map, Context context, KmlDocument document){
        mapView = map;
        mMarker = marker;
        Drawable defaultMarker = context.getResources().getDrawable(R.drawable.marker);
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
    public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        marker.setTitle(kmlPlacemark.mExtendedData.get("nom"));
        //marker.setRelatedObject();
        Drawable normalMarker = mapView.getContext().getResources().getDrawable(R.drawable.marker_node);
        marker.setInfoWindow(new SiteInfoWindow(mapView));


    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

    }

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

    }
}
