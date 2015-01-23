package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Place;

import java.util.List;

/**
 * Created by castex on 22/01/15.
 */
public class PhotoTouchEvent {


    int index;

    public PhotoTouchEvent( int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
