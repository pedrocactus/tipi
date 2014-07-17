package com.example.topopoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.bonuspack.kml.KmlFeature;

/**
 * Created by pierrecastex on 25/05/2014.
 */
public class PanelDescriptionFragment extends Fragment {

    private KmlFeature feature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if(bundle != null){
            feature = bundle.getParcelable("description");
        }

        View rootView = inflater.inflate(R.layout.panel_description_layout, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.name);
        title.setText(feature.mExtendedData.get("nom"));

        TextView subtitle = (TextView) rootView.findViewById(R.id.nbvoies);
        subtitle.setText(feature.mExtendedData.get("nbvoies"));

        TextView description_secteur = (TextView) rootView.findViewById(R.id.description_secteur);
        description_secteur.setText(feature.mExtendedData.get("description"));

        Button zoomToButton =  (Button) rootView.findViewById(R.id.follow);

        zoomToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BusProvider.getInstance().post(new ZoomToEvent(feature.mExtendedData.get("nom")));
            }
        });
        return rootView;


    }
}
