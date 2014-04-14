package com.example.topopoc;

import android.app.Application;

/**
 * Created by pcastex on 14/04/14.
 */
public class App extends Application {



    private int VOIES_VIEW_STATE = -1;

    public int getVoiesViewState(){
        return VOIES_VIEW_STATE;
    }

    public void setVoiesViewState(InfoPositionState viewState){
        VOIES_VIEW_STATE = viewState.state;

    }


}
