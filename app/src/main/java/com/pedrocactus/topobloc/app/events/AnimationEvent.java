package com.pedrocactus.topobloc.app.events;

/**
 * Created by pcastex on 14/04/14.
 */
public class AnimationEvent {
    private int STEP;
    private String DIRECTION;

    public AnimationEvent(int step, String direction){
        STEP = step;
        DIRECTION = direction;
    }

    public String getDirection(){
        return DIRECTION;
    }
    public void setDirection(String direction){
        DIRECTION=direction;
    }
    public int getStep(){
        return STEP;
    }
    public void setStep(int step){
        STEP=step;
    }
}
