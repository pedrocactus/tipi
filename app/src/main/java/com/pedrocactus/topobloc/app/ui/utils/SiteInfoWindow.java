package com.pedrocactus.topobloc.app.ui.utils;

import android.view.View;
import android.widget.Button;

import com.pedrocactus.topobloc.app.R;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;

/**
 * Created by pierrecastex on 02/04/2014.
 */
public class SiteInfoWindow extends MarkerInfoWindow {

    private String mSelectedPoi;
    private MapView mMapView;

    public SiteInfoWindow(MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);
        Button btn = (Button)(mView.findViewById(R.id.bubble_moreinfo));
        mMapView = mapView;
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //if (mSelectedPoi.mUrl != null){
                  //  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPoi.mUrl));
                    //((MainActivity)view.getContext()).goToVoieFragment(view);
                //}

                BoundingBoxE6 bounds = new BoundingBoxE6(48.6604,-4.3871,48.6574,-4.3938);
                mMapView.zoomToBoundingBox(bounds);

            }
        });
    }

    @Override public void onOpen(Object item){
        super.onOpen(item);
        Marker marker = (Marker)item;
        //mSelectedPoi = (String)marker.getRelatedObject();
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
    }
}
