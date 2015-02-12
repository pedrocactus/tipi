package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchAreaEvent;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class NationalSiteJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    public String nationalSiteName;
    @Inject
    TopoblocAPI boxotopApiClient;

    public NationalSiteJob(String nationalSiteName) {
        super();
        TopoblocApp.injectMembers(this);
        this.nationalSiteName = nationalSiteName;
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
       NationalSite nationalSite = boxotopApiClient.getNationalSite(nationalSiteName);
        FetchAreaEvent event = new FetchAreaEvent(nationalSite);
        eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}