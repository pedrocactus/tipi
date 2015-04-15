package com.pedrocactus.topobloc.app.ui.panel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.ui.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by castex on 10/02/15.
 */
public class RightPanelListAdapter extends BaseAdapter implements Filterable {

    private List<Place> places;
    private List<Place> placesTmpSearch;
    private final Context context;

    public RightPanelListAdapter(Context context) {
        this.context = context;
        this.places = new ArrayList<Place>();
        this.placesTmpSearch = new ArrayList<Place>();
    }

    @Override
    public int getCount() {
        return placesTmpSearch.size();
    }

    @Override
    public Place getItem(int position) {
        return placesTmpSearch.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.place_list_item, parent, false);
        }
//        ImageView movieThumbNailView = (ImageView) ViewHolder.get(convertView, R.id.movie_thumbnail);
        TextView placeTitleTextView = ViewHolder.get(convertView, R.id.place_title);
        TextView levelTextView = ViewHolder.get(convertView, R.id.level);
        Place place = getItem(position);
        String number = "";
        if (place instanceof Route) {
            number = Integer.toString(((Route) place).getNumber()) + ". ";

            levelTextView.setText(((Route) place).getLevel());
            placeTitleTextView.setTextColor(context.getResources().getColor(R.color.red));
        }
        placeTitleTextView.setText(number + place.getName());


        return convertView;
    }

    public void updatePlaces(List<Place> places) {
//this.places = places;
        this.places.clear();
        this.places.addAll(places);
        this.placesTmpSearch.clear();
        this.placesTmpSearch.addAll(places);
        notifyDataSetChanged();
    }

    public void updatePlace(final int position, final Place movie) {
        this.places.add(position, movie);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
// No filter implemented we return all the list
                    placesTmpSearch.clear();
                    placesTmpSearch.addAll(places);
                } else {
                    placesTmpSearch.clear();
                    for (Place place : places) {
                        if (place.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            placesTmpSearch.add(place);
                    }
                }
                return results;
            }
        };
        return filter;
    }
}

