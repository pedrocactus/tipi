package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Sector;

/**
 * Created by pierrecastex on 11/01/2015.
 */
public class FetchSectorEvent {

    Sector sector;

    public FetchSectorEvent(Sector sector) {
        this.sector = sector;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
