package com.pedrocactus.topobloc.app.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.ui.about.AboutListAdapter;
import com.pedrocactus.topobloc.app.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by pierrecastex on 19/01/2015.
 */
public class ListFragment  extends BaseFragment {


    public static String TAG = "ListFragment";

    @InjectView(R.id.about_version_title)
    TextView aboutVersion;

    @InjectView(R.id.place_listview)
    ListView placeListView;


    private PlaceListAdapter adapter;


    private List<Place> places;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.inject(this, view);

        aboutVersion.setText(getString(R.string.version));

        adapter = new PlaceListAdapter(getActivity());
        placeListView.setAdapter(adapter);

        places = getArguments().getParcelableArrayList("places");


        return view;

    }

    @OnItemClick(R.id.place_listview)
    public void onMovieClicked(int position){
        eventBus.post(new NavigationToDetailEvent(position));
    }

    private HashMap<String, Object> getDetailItemInfo(AboutListAdapter.AboutType type, String title, Object body) {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put(AboutListAdapter.TYPE, type);
        item.put(AboutListAdapter.TITLE, title);
        item.put(AboutListAdapter.BODY, body);
        return item;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
