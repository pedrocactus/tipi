package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchRouteListEvent;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;
import com.pedrocactus.topobloc.app.model.Route;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class SectorJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    @Inject
    TopoblocAPI boxotopApiClient;

    public SectorJob() {
        super();
        TopoblocApp.injectMembers(this);
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
        List<Route> routes = boxotopApiClient.getListRoutes();

        for (Route route : routes ) {
            System.out.println(route.getName() + " (" + route.getLevel()+ ")");
        }

        FetchRouteListEvent event = new FetchRouteListEvent(routes);
        eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}