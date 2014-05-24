package com.example.topopoc;

/**
 * Created by pierrecastex on 24/05/2014.
 */
public class ZoomToEvent {
    private String namePoint;
    private String DIRECTION;

    public ZoomToEvent( String nPoint){
        namePoint = nPoint;
    }

    public String getNamePoint(){
        return namePoint;
    }
}
