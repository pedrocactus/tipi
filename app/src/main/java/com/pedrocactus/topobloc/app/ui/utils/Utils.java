package com.pedrocactus.topobloc.app.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by pierrecastex on 24/07/2014.
 */
public class Utils {


    public static int getResIdFromName(String name, Context context){
        int test =  context.getResources().getIdentifier(name.toLowerCase().replace(" ", "_"),"","");
        return test;
    }


    public static float getPixels(int unit, float size) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(unit, size, metrics);
    }

    public static BoundingBox getBoundingBox(float[] bounds){
        LatLng northEast = new LatLng(bounds[5],bounds[4]);
        LatLng southWest = new LatLng(bounds[1],bounds[0]);
        return new BoundingBox(northEast,southWest);
    }
}
