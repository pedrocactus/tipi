package com.example.topopoc.adapters;

/**
 * Created by pierrecastex on 20/05/2014.
 */
public class DrawerItem {
    public static int NO_IMAGE = -10;

    String ItemName;
    int imgResID;
    boolean isTitle;

    public DrawerItem(String itemName, int imgResID,boolean isTitle) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
        this.isTitle = isTitle;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

}