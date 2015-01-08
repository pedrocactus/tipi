package com.pedrocactus.topobloc.app.ui.utils;

import android.view.View;
import android.widget.Button;

import com.pedrocactus.topobloc.app.ui.MainActivity;
import com.pedrocactus.topobloc.app.R;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

/**
 * Created by pierrecastex on 02/04/2014.
 */
public class VoieInfoWindow extends MarkerInfoWindow {

    private String mSelectedPoi;

    public VoieInfoWindow(MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);
        Button btn = (Button)(mView.findViewById(R.id.bubble_moreinfo));
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //if (mSelectedPoi.mUrl != null){
                  //  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPoi.mUrl));
                    ((MainActivity)view.getContext()).goToVoieFragment(view);
                //}

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
