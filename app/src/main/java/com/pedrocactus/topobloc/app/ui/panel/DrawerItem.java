package com.pedrocactus.topobloc.app.ui.panel;

/**
 * Created by pierrecastex on 20/05/2014.
 */
public class DrawerItem {
    public static int NO_IMAGE = -10;

    boolean isBottom;
    String ItemName;
    int imgResID;

    public DrawerItem(String itemName, int imgResID,boolean isBottom) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
        this.isBottom = isBottom;
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