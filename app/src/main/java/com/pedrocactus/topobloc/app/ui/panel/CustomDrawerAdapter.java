package com.pedrocactus.topobloc.app.ui.panel;

/**
 * Created by pierrecastex on 20/05/2014.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.ui.utils.ViewHolder;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;
    int titleLayoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, int titleLayoutResourceID,
                               List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        this.titleLayoutResID = titleLayoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItem(position).isBottom) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(titleLayoutResID, parent, false);
            }


        }else{
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(layoutResID, parent, false);
            }
        }

        TextView titleTextView = (TextView) ViewHolder.get(convertView, R.id.drawer_itemName);
        ImageView iconImage = ViewHolder.get(convertView, R.id.drawer_icon);
        titleTextView.setText( getItem(position).ItemName);

    if(getItem(position).getImgResID()!=DrawerItem.NO_IMAGE)
        iconImage.setImageDrawable(context.getResources().getDrawable(
                getItem(position).getImgResID()));

        return convertView;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
        boolean isTitle;
    }
}
