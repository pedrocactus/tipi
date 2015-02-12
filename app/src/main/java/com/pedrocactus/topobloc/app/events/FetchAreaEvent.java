package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Area;

/**
 * Created by castex on 11/02/15.
 */
public class FetchAreaEvent {

    Area area;

    public FetchAreaEvent(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
