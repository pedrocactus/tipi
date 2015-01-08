package com.pedrocactus.topobloc.app.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.pedrocactus.topobloc.app.TopoblocApp;
import com.pedrocactus.topobloc.app.events.NetworkErrorEvent;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by castex on 08/01/15.
 */
public abstract class BaseNetworkJob extends Job {
    public static final int PRIORITY = 1;
    @Inject
    protected EventBus eventBus;
    protected BaseNetworkJob() {
        super(new Params(PRIORITY));
        TopoblocApp.injectMembers(TopoblocApp.getInstance());
    }
    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        eventBus.post(new NetworkErrorEvent(NetworkErrorEvent.OTHER));
        return false;
    }
}