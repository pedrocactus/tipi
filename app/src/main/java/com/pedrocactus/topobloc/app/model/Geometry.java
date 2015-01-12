package com.pedrocactus.topobloc.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrecastex on 08/01/2015.
 */
public class Geometry<T> {
    protected List<T> coordinates = new ArrayList<T>();

    public Geometry() {
    }

    public Geometry(T... elements) {
        for (T coordinate : elements) {
            coordinates.add(coordinate);
        }
    }

    public Geometry<T> add(T elements) {
        coordinates.add(elements);
        return this;
    }

    public List<T> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<T> coordinates) {
        this.coordinates = coordinates;
    }

}
