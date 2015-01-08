package com.pedrocactus.topobloc.app.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

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
public class VoieBulle implements KmlFeature.Styler {
    private Drawable mMarker;
    private MapView mapView;
    private Style normalStyle;
    private Style defaultStyle;
    private KmlDocument docu;
    private String mFilter;
    public VoieBulle(Drawable marker,MapView map, Context context, String filter, KmlDocument document){
        mapView = map;
        mMarker = marker;
        Drawable defaultMarker = context.getResources().getDrawable(R.drawable.marker);
        Bitmap defaultBitmap = ((BitmapDrawable)defaultMarker).getBitmap();
        defaultStyle = new Style(defaultBitmap, 0x901010AA, 3.0f, 0x20AA1010);


        Drawable normalMarker = context.getResources().getDrawable(R.drawable.marker_node);
        Bitmap normalBitmap = ((BitmapDrawable)normalMarker).getBitmap();
        normalStyle = new Style(defaultBitmap, 0x901010AA, 3.0f, 0x20AA1010);
        mFilter = filter;
        docu = document;
    }


    @Override
    public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
/*
        ArrayList<KmlFeature> iTems = ((KmlFolder)kmlFeature).mItems;
        int nbItmes = iTems.size();
        Iterator<KmlFeature> iter = iTems.iterator();
        KmlFeature ffeature = null;
         if(!mFilter.equals("tout")) {
             if(iter.next().mExtendedData==null){
                 iTems = ((KmlFolder)((KmlFolder)kmlFeature).mItems.get(0)).mItems;
                 iter = iTems.iterator();
             }
            while (iter.hasNext()) {

                ffeature = iter.next();
                if (ffeature.mExtendedData.get("style") == null || !ffeature.mExtendedData.get("style").contains(mFilter)) {
                    //iter.remove();
                    ((KmlPlacemark)ffeature).mStyle = "node";
                }else{

                    ((KmlPlacemark)ffeature).mStyle = "marker";
                }
            }
        }
*/

    }

    @Override
    public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        marker.setSubDescription(kmlPlacemark.mExtendedData.get("niveau") + kmlPlacemark.mExtendedData.get("start") + kmlPlacemark.mExtendedData.get("style"));
        marker.setTitle(kmlPlacemark.mExtendedData.get("nom"));
        //marker.setRelatedObject();
        Drawable normalMarker = mapView.getContext().getResources().getDrawable(R.drawable.marker_node);
        marker.setInfoWindow(new VoieInfoWindow(mapView));
        if (kmlPlacemark.mExtendedData.get("style") == null || !kmlPlacemark.mExtendedData.get("style").contains(mFilter)) {
            //iter.remove();
            kmlPlacemark.mStyle = "node";
            marker.setIcon(normalMarker);
        }else{

            kmlPlacemark.mStyle = "marker";
            marker.setIcon(mMarker);

        }
        //kmlPoint.applyDefaultStyling(marker, normalStyle, kmlPlacemark, docu, mapView);
    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

    }

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

    }
}
