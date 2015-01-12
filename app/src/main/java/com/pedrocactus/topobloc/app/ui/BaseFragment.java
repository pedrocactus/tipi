package com.pedrocactus.topobloc.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


import com.pedrocactus.topobloc.app.TopoblocApp;


/**
 * Created by pierrecastex on 23/09/2014.
 */
public abstract class BaseFragment extends Fragment {

    @Inject
    protected EventBus eventBus;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopoblocApp.injectMembers(this);
    }



    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }
}
