package com.pedrocactus.topobloc.app.events;

/**
 * Created by pierrecastex on 24/05/2014.
 */
public class ZoomOutEvent {
    private String namePoint;
    private float[] boundingbox;

    public ZoomOutEvent(String nPoint, float[] boundingbox){
        namePoint = nPoint;
        this.boundingbox = boundingbox;
    }

    public String getNamePoint(){
        return namePoint;
    }
    public float[] getBoundingBox(){
        return boundingbox;
    }

}
