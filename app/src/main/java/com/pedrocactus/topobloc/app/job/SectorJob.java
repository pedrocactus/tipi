package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.events.FetchRouteListEvent;
import com.pedrocactus.topobloc.app.events.FetchSectorEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;
import com.pedrocactus.topobloc.app.model.Route;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class SectorJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    public String sectorName;
    @Inject
    TopoblocAPI boxotopApiClient;

    public SectorJob(String sectorName) {
        super();
        TopoblocApp.injectMembers(this);
        this.sectorName = sectorName;
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
       Sector sector = boxotopApiClient.getSectorByName(sectorName);

        for (Route route : sector.getRoutes() ) {
            System.out.println(route.getName() + " (" + route.getLevel()+ ")");
        }

        FetchPlacesEvent event = new FetchPlacesEvent((List<Place>)(List<?>)sector.getRoutes());
        eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}