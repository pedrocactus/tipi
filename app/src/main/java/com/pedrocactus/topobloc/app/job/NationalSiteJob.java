package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class NationalSiteJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    @Inject
    TopoblocAPI boxotopApiClient;

    public NationalSiteJob() {
        super();
        TopoblocApp.injectMembers(this);
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
       List<NationalSite> nationalSites = boxotopApiClient.getNationalSites();


        FetchPlacesEvent event = new FetchPlacesEvent((List<Place>)(List<?>) nationalSites);
        eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}