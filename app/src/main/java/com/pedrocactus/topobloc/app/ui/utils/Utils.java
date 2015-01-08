package com.pedrocactus.topobloc.app.ui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by pierrecastex on 24/07/2014.
 */
public class Utils {


    public static int getResIdFromName(String name, Context context){
        return context.getResources().getIdentifier(name.toLowerCase().replace(" ", "_"),"","");
    }
}
