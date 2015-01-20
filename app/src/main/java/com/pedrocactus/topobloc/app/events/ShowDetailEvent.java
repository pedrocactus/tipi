package com.pedrocactus.topobloc.app.events;

/**
 * Created by castex on 20/01/15.
 */
public class ShowDetailEvent {

    public int indexToShow;

    public ShowDetailEvent(int indexToShow){
        this.indexToShow = indexToShow;
    }

    public int getIndexToShow(){
        return  indexToShow;
    }
}
