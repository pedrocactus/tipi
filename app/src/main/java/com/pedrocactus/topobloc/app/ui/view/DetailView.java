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
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by castex on 14/01/15.
 */
public class DetailView extends LinearLayout {


    @InjectView(R.id.name)
    TextView title;

    @InjectView(R.id.place_imageview)
    ImageView placeImage;

    ListView placeDescriptionListView;

    private DetailListAdapter adapter;


    public DetailView(Context context) {
        super(context);
        View headerView = LayoutInflater.from(context).inflate(R.layout.panel_description_layout, null, true);
        LayoutInflater.from(context).inflate(R.layout.root_layout_slideup, this, true);
        ButterKnife.inject(this, headerView);

        placeDescriptionListView = (ListView)findViewById(R.id.listView_detail_info);
        placeDescriptionListView.addHeaderView(headerView);
        adapter = new DetailListAdapter(context);
        placeDescriptionListView.setAdapter(adapter);


}
    public void bindModel(Place place){
        title.setText(place.getName());
        Picasso.with(getContext())
                .load(place.images.get(0))
                .into(placeImage);

        //Setting up the listview with different layotus
        ArrayList<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
        if(((NationalSite)place).getDescription().length()!=0)
            detailList.add(getDetailItemInfo(DetailListAdapter.DetailType.DESCRIPTION,getContext().getString(R.string.detail_description),((NationalSite)place).getDescription()));
//        if(movie.getActors()!=null&&movie.getActors().size()!=0)
//            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.CAST,getContext().getString(R.string.detail_casting),movie.getActors()));
//        if(movie.getSimilarMovies()!=null&&movie.getSimilarMovies().size()!=0)
//            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.SIMILAR_MOVIES,getContext().getString(R.string.detail_similar_movies),movie.getSimilarMovies()));
        adapter.putData(detailList);
    }

    private HashMap<String, Object> getDetailItemInfo(DetailListAdapter.DetailType type,String title, Object body) {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put(DetailListAdapter.TYPE, type);
        item.put(DetailListAdapter.TITLE, title);
        item.put(DetailListAdapter.BODY, body);
        return item;
    }

}
