package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Place;

import java.util.List;

/**
 * Created by pierrecastex on 12/01/2015.
 */
public class FetchPlacesEvent {
    List<Place> places;

    public FetchPlacesEvent(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
