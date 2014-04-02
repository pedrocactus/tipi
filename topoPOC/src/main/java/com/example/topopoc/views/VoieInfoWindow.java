package com.example.topopoc.views;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.topopoc.MainActivity;
import com.example.topopoc.R;

import org.osmdroid.bonuspack.location.POI;
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
