package com.pedrocactus.topobloc.app.events;

/**
 * Created by castex on 20/01/15.
 */
public class SwipeDetailEvent {

    public int indexToShow;
    public int previousIndex;

    public SwipeDetailEvent(int indexToShow, int previousIndex){
        this.indexToShow = indexToShow;
        this.previousIndex = previousIndex;
    }

    public int getIndexToShow(){
        return  indexToShow;
    }

    public int getPreviousIndex(){
        return  previousIndex;
    }
}
