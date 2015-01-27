package com.pedrocactus.topobloc.app.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.ui.slideuppanel.view.DetailListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by castex on 27/01/15.
 */
public class AboutFragment extends Fragment {


    public static String TAG = "AboutFragment";

    @InjectView(R.id.about_version_title)
    TextView aboutVersion;

    @InjectView(R.id.listView_about)
    ListView aboutListView;


    private AboutListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopoblocApp.injectMembers(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container,false);
        ButterKnife.inject(this, view);

        aboutVersion.setText(getString(R.string.version));

        adapter = new AboutListAdapter(getActivity());
        aboutListView.setAdapter(adapter);

        ArrayList<Map<String, Object>> aboutlist = new ArrayList<Map<String, Object>>();
        aboutlist.add(getDetailItemInfo(AboutListAdapter.AboutType.PURPOSE,getActivity().getString(R.string.about_purpose_title),getActivity().getString(R.string.about_purpose_body)));

        aboutlist.add(getDetailItemInfo(AboutListAdapter.AboutType.LICENSE,getActivity().getString(R.string.about_license_title),getActivity().getString(R.string.about_license_body)));
        adapter.putData(aboutlist);


        return view;

    }

    private HashMap<String, Object> getDetailItemInfo(AboutListAdapter.AboutType type,String title, Object body) {
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
