package com.example.topopoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pierrecastex on 02/04/2014.
 */
public class VoieFragment extends Fragment {
    private String voie;

    public VoieFragment(String voie){
        this.voie = voie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.voie_fragment, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewVoie);
        imageView.setImageResource(R.drawable.fanny6a);
        TextView text = (TextView) view.findViewById(R.id.level);
        text.setText(voie);
        return view;
    }
}
