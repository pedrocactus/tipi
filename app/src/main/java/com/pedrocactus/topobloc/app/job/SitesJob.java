package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Site;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class SitesJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    @Inject
    TopoblocAPI boxotopApiClient;

    private String nationalSiteName;

    public SitesJob(String nationalSiteName) {
        super();
        TopoblocApp.injectMembers(this);
        this.nationalSiteName = nationalSiteName;
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
       List<Site> sites = boxotopApiClient.getSitesFromNational(nationalSiteName);


        FetchPlacesEvent event = new FetchPlacesEvent((List<Place>)(List<?>) sites);
        eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}