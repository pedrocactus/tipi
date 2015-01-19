package com.pedrocactus.topobloc.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.pedrocactus.topobloc.app.TopoblocApp;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by pierrecastex on 17/01/2015.
 */
public class BaseActivity extends ActionBarActivity{


    @Inject
    protected EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopoblocApp.injectMembers(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }
}
