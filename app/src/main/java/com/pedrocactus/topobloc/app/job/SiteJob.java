package com.pedrocactus.topobloc.app.job;

import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.FetchAreaEvent;
import com.pedrocactus.topobloc.app.events.FetchPlacesEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Site;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by castex on 08/01/15.
 */
public class SiteJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;
    @Inject
    TopoblocAPI boxotopApiClient;

    private String siteName;

    public SiteJob(String siteName) {
        super();
        TopoblocApp.injectMembers(this);
        this.siteName = siteName;
    }
    @Override
    public void onAdded() {
    }
    @Override
    public void onRun() throws Throwable {
       Site site = boxotopApiClient.getSite(siteName);
       FetchAreaEvent event = new FetchAreaEvent(site);
       eventBus.post(event);
    }
    @Override
    protected void onCancel() {
    }
}