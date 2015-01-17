package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Place;

/**
 * Created by castex on 14/01/15.
 */
public class FetchDetailPlaceEvent {

    Place place;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
