package com.example.topopoc.views;

import android.graphics.drawable.Drawable;

import com.example.topopoc.R;

import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

/**
 * Created by pierrecastex on 24/03/2014.
 */
public class VoieBulle implements KmlFeature.Styler {
    private Drawable mMarker;
    private MapView mapView;
    public VoieBulle(Drawable marker,MapView map){
        mapView = map;
        mMarker = marker;
    }


    @Override
    public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

    }

    @Override
    public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        marker.setSubDescription(kmlPlacemark.mExtendedData.get("niveau") + kmlPlacemark.mExtendedData.get("start") + kmlPlacemark.mExtendedData.get("style"));
        marker.setTitle(kmlPlacemark.mExtendedData.get("nom"));
        //marker.setRelatedObject();

        marker.setInfoWindow(new VoieInfoWindow(mapView));
    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

    }

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

    }
}
