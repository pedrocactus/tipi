package com.pedrocactus.topobloc.app.ui.slideuppanel.view;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.ZoomToEvent;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Route;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by castex on 14/01/15.
 */
public class RouteDetailView extends LinearLayout implements CustomDetailView{


    //@InjectView(R.id.name)
    TextView title;

    //@InjectView(R.id.place_imageview)
    ImageView placeImage;

    TextView level;


    LinearLayout caracLayout;


    private Context context;


    public RouteDetailView(Context context) {
        super(context);
        this.context = context;
        //View headerView = LayoutInflater.from(context).inflate(R.layout., null, true);
        LayoutInflater.from(context).inflate(R.layout.panel_description_layout_route, this, true);
        //ButterKnife.inject(this, this);

        title = (TextView)findViewById(R.id.name);
        placeImage = (ImageView)findViewById(R.id.place_imageview);

        level = (TextView)findViewById(R.id.level);

        caracLayout = (LinearLayout) findViewById(R.id.route_carac_layout);


}
    @Override
    public void bindModel(final Place place, final int index){
        Route route = (Route)place;
        level.setText(route.getLevel());
        ArrayList<RouteType> routeTypes = new ArrayList<RouteType>();
        if(route.isDalle()) {
            routeTypes.add(RouteType.DALLE);
        }

        if(route.isDevers()) {
            routeTypes.add(RouteType.DEVERS);
        }
        if(route.isDanger()) {
            routeTypes.add(RouteType.LETHAL);
        }
        if(route.isHighball()) {
            routeTypes.add(RouteType.HIGHBALL);
        }
        if(route.isOffshore()) {
            routeTypes.add(RouteType.OFFSHORE);
        }


        for(int i = 0;i<routeTypes.size();i++) {
            ImageButton b1 = new ImageButton(context);
            b1.setId(100 + i);
            b1.setImageResource(RouteType.DALLE.getRes());
            // b1.setText(adapt_objmenu.city_name_array[i]);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            if (i > 0) {
//                lp.addRule(RelativeLayout.RIGHT_OF, b1.getId() - 1);
//            }

//                lp.addRule(RelativeLayout.CENTER_IN_PARENT, b1.getId());

            b1.setLayoutParams(lp);

            caracLayout.addView(b1);
        }



        title.setText(place.getName());
        title.setTextColor(context.getResources().getColor(context.getResources().getIdentifier(route.getCircuit(),"color",context.getPackageName())));
        if(place.images!=null)
        Picasso.with(context)
                .load(place.images.get(1))
                .into(placeImage);


    }


    public void setupListener(OnClickListener listener){
        level.setOnClickListener(listener);
    }

    public enum RouteType {
        DEVERS("description", R.drawable.flex_biceps), DALLE("history", R.drawable.flex_biceps), LETHAL("history",  R.drawable.flex_biceps), HIGHBALL("history",  R.drawable.flex_biceps), OFFSHORE("history", R.drawable.water_element);
        private final int res;
        private final String value;

        private RouteType(String value, int res) {
            this.res = res;
            this.value = value;
        }

        public int getRes() {
            return res;
        }

        public String getValue() {
            return value;
        }


    }
}
