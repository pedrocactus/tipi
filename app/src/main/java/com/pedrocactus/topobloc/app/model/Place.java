package com.pedrocactus.topobloc.app.model;

/**
 * Created by pierrecastex on 12/01/2015.
 */
public abstract class Place {

    public String name;
    public float[] coordinates;

    public Place(String name, float[] coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public Place() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }
}
