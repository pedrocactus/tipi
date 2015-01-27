package com.pedrocactus.topobloc.app.ui.slideuppanel.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.PhotoTouchEvent;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by castex on 14/01/15.
 */
public class DetailView extends LinearLayout implements CustomDetailView{


    //@InjectView(R.id.name)
    TextView title;

    //@InjectView(R.id.place_imageview)
    ImageView placeImage;

    Button zoomToButton;

    ListView placeDescriptionListView;

    private DetailListAdapter adapter;

    private Context context;


    public DetailView(Context context) {
        super(context);
        this.context = context;
        //View headerView = LayoutInflater.from(context).inflate(R.layout., null, true);
        LayoutInflater.from(context).inflate(R.layout.panel_description_layout, this, true);
        //ButterKnife.inject(this, this);

        placeDescriptionListView = (ListView)findViewById(R.id.listView_detail_info);
        title = (TextView)findViewById(R.id.name);
        placeImage = (ImageView)findViewById(R.id.place_imageview);
        //placeDescriptionListView.addHeaderView(headerView);
        adapter = new DetailListAdapter(context);
        placeDescriptionListView.setAdapter(adapter);

        zoomToButton = (Button)findViewById(R.id.follow);



}
    @Override
    public void bindModel(final Place place, final int placeIndex){
        zoomToButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ZoomToEvent(place.getName(),place.getBoundingbox()));
            }
        });

        placeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new PhotoTouchEvent( placeIndex));
            }
        });


        title.setText(place.getName());
        if(place.images!=null)
        Picasso.with(context)
                .load(place.images.get(1))
                .into(placeImage);

        //Setting up the listview with different layotus
        ArrayList<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
        if(place.getDescription()!=null && place.getDescription().length()!=0)
            detailList.add(getDetailItemInfo(DetailListAdapter.DetailType.DESCRIPTION,getContext().getString(R.string.detail_description),place.getDescription()));


        if(place.getHistory()!=null && place.getHistory().length()!=0)
        detailList.add(getDetailItemInfo(DetailListAdapter.DetailType.HISTORY,getContext().getString(R.string.detail_history),place.getHistory()));
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
