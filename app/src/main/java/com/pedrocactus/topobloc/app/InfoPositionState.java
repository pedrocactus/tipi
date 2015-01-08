package com.pedrocactus.topobloc.app;

/**
 * Created by pcastex on 14/04/14.
 */
public enum InfoPositionState {
    DOWN (0),
    COTATION (1),
    NOM (2),
    DESCRITPTION (3);

    public int state;

    InfoPositionState( int state){
        this.state = state;
    }
}
