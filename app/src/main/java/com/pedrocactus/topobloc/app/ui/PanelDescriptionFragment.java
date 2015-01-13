package com.pedrocactus.topobloc.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.BusProvider;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.ui.utils.Utils;

import org.osmdroid.bonuspack.kml.KmlFeature;

import java.util.ArrayList;

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
        ArrayList<String> listFeatures = new ArrayList<String>();
        listFeatures.add(feature.mExtendedData.get("min_voies")+"-"+feature.mExtendedData.get("max_voies"));
        listFeatures.add("devers = " + feature.mExtendedData.get("devers") + " surplomb = " + feature.mExtendedData.get("surplomb") + " dalle = " + feature.mExtendedData.get("dalle"));

        ListView secteur_features_listview = (ListView) rootView.findViewById(R.id.secteur_features_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listFeatures);
        secteur_features_listview.setAdapter(adapter);

        Button zoomToButton =  (Button) rootView.findViewById(R.id.follow);

        zoomToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BusProvider.getInstance().post(new ZoomToEvent(feature.mExtendedData.get("nom")));
            }
        });

        ImageView image = (ImageView) rootView.findViewById(R.id.imageView);
        int resid = Utils.getResIdFromName(feature.mExtendedData.get("nom"), getActivity());
        //Toast.makeText(getActivity(),"Ressource id "+resid,Toast.LENGTH_SHORT).show();
        //image.setImageResource(R.drawable.menez_ham);

        return rootView;


    }
}
