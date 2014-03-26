package com.example.topopoc.views;

import com.example.topopoc.R;

import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

/**
 * Created by pierrecastex on 24/03/2014.
 */
public class VoieBulle extends MarkerInfoWindow {

    public VoieBulle(MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);
    }


}
