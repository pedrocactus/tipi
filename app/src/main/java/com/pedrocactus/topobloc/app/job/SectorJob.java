package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class SectorJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    public String siteName;

    @Inject
    TopoblocAPI boxotopApiClient;

    public SectorJob(String siteName) {
        super();
        TopoblocApp.injectMembers(this);
        this.siteName = siteName;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
       List<Sector> sectors = boxotopApiClient.getSectorsFromSite(siteName);


        FetchPlacesEvent event = new FetchPlacesEvent((List<Place>)(List<?>) sectors);
        eventBus.post(event);
    }

    @Override
    protected void onCancel() {
    }
}