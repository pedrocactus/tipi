package com.pedrocactus.topobloc.app.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.BusProvider;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by castex on 14/01/15.
 */
public class DetailView extends LinearLayout {


    @InjectView(R.id.name)
    TextView title;

    public DetailView(Context context) {
        super(context);
        View headerView = LayoutInflater.from(context).inflate(R.layout.item_detail_fragment, null, true);
        LayoutInflater.from(context).inflate(R.layout.list_detail_info, this, true);
    View rootView =  LayoutInflater.from(context).inflate(R.layout.panel_description_layout, null, true);

    ButterKnife.inject(this, rootView);

    title.setText(place..get("nom"));


    TextView description_secteur = (TextView) rootView.findViewById(R.id.description_secteur);
    description_secteur.setText(feature.mExtendedData.get("description"));
    ArrayList<String> listFeatures = new ArrayList<String>();
    listFeatures.add(feature.mExtendedData.get("min_voies")+"-"+feature.mExtendedData.get("max_voies"));
    listFeatures.add("devers = "+feature.mExtendedData.get("devers")+" surplomb = "+feature.mExtendedData.get("surplomb")+" dalle = "+feature.mExtendedData.get("dalle"));

    ListView secteur_features_listview = (ListView) rootView.findViewById(R.id.secteur_features_listview);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listFeatures);
    secteur_features_listview.setAdapter(adapter);

    Button zoomToButton = (Button) rootView.findViewById(R.id.follow);



    ImageView image = (ImageView) rootView.findViewById(R.id.imageView);
    int resid = Utils.getResIdFromName(feature.mExtendedData.get("nom"), getActivity());
    //Toast.makeText(getActivity(),"Ressource id "+resid,Toast.LENGTH_SHORT).show();
    //image.setImageResource(R.drawable.menez_ham);

}
    public void bindModel(Place place){
//        noNetworkLayout.setVisibility(View.GONE);
        movieDescriptionListView.setVisibility(VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        movieTitle.setText(movie.getTitle());
        Picasso.with(getContext())
                .load(place.ge.getPosters().getThumbnail())
                .into(movieImage);

//Setting up the listview with different layotus
        ArrayList<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
        if(movie.getSynopsis().length()!=0)
            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.SYNOPSIS,getContext().getString(R.string.detail_synopsis),movie.getSynopsis()));
        if(movie.getActors()!=null&&movie.getActors().size()!=0)
            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.CAST,getContext().getString(R.string.detail_casting),movie.getActors()));
        if(movie.getSimilarMovies()!=null&&movie.getSimilarMovies().size()!=0)
            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.SIMILAR_MOVIES,getContext().getString(R.string.detail_similar_movies),movie.getSimilarMovies()));
        adapter.putData(detailList);
        ratingBarLayout.setClickable(true);
    }

}
