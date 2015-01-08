package com.pedrocactus.topobloc.app.adapters;

/**
 * Created by pierrecastex on 20/05/2014.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;

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
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();
            if(dItem.isTitle) {
                view = inflater.inflate(titleLayoutResID, parent, false);
            }else{
                view = inflater.inflate(layoutResID, parent, false);
            }
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

    if(dItem.getImgResID()!=DrawerItem.NO_IMAGE)
        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResID()));
        drawerHolder.ItemName.setText(dItem.getItemName());
        drawerHolder.isTitle = dItem.isTitle;

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
        boolean isTitle;
    }
}
