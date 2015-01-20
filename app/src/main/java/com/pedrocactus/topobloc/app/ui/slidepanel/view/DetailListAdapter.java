package com.pedrocactus.topobloc.app.ui.slidepanel.view;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.ui.utils.ViewHolder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by castex on 16/01/15.
 */
public class DetailListAdapter extends BaseAdapter {
    private ArrayList<Map<String, Object>> detailItems;
    private Context context;

    public enum DetailType {
        DESCRIPTION("description", R.layout.item_list_detail_info), HISTORY("history", R.layout.item_list_detail_info);
        private final int res;
        private final String value;

        private DetailType(String value, int res) {
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

    public static String TYPE = "type";
    public static String TITLE = "title";
    public static String BODY = "body";

    public DetailListAdapter(Context context) {
        this.context = context;
        this.detailItems = new ArrayList<Map<String, Object>>();
    }

    @Override
    public int getCount() {
        return detailItems.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return detailItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (((DetailType) getItem(position).get(TYPE)).getValue().equals(DetailType.DESCRIPTION.getValue())|| ((DetailType) getItem(position).get(TYPE)).getValue().equals(DetailType.HISTORY.getValue())) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.item_list_detail_info, parent, false);
            }
            TextView titleTextView = (TextView) ViewHolder.get(convertView, R.id.detail_review_title);
            TextView bodyTextView = ViewHolder.get(convertView, R.id.detail_review_body);
            titleTextView.setText(Html.fromHtml((String) getItem(position).get(TITLE)));
            bodyTextView.setText((String) getItem(position).get(BODY));

        }

        return convertView;
    }

    public void putData(ArrayList<Map<String, Object>> datas) {
        detailItems.clear();
        detailItems.addAll(datas);
        notifyDataSetChanged();
    }
}
