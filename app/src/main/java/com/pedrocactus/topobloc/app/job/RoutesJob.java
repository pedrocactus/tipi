package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class RoutesJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    public String sectorName;
    @Inject
    TopoblocAPI boxotopApiClient;

    public RoutesJob(String sectorName) {
        super();
        TopoblocApp.injectMembers(this);
        this.sectorName = sectorName;
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
       List<Route> routes = boxotopApiClient.getRoutesFromSector(sectorName);

        for (Route route : routes ){
            System.out.println(route.getName() + " (" + route.getLevel()+ ")");
        }

        FetchPlacesEvent event = new FetchPlacesEvent((List<Place>)(List<?>)routes);
        eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}