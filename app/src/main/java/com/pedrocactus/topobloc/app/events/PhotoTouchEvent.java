package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Place;

import java.util.List;

/**
 * Created by castex on 22/01/15.
 */
public class PhotoTouchEvent {

    List<Place> places;

    int index;

    public PhotoTouchEvent(List<Place> places, int index) {
        this.places = places;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
