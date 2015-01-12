package com.pedrocactus.topobloc.app.model;

/**
 * Created by pierrecastex on 08/01/2015.
 */
public class Feature {

    private Geometry geometry;
    private String id;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
