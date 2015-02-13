package com.pedrocactus.topobloc.app.model;

import java.util.List;

/**
 * Created by castex on 11/02/15.
 */
public abstract class Area extends Place{


    public float[] boundingbox;
    public List<String> ancestors;

    public Area(String name, float[] coordinates,List<String> images, float[] boundingbox,String description,String history,List<String> ancestors) {
        super(name,coordinates,images,description,history);
        this.boundingbox = boundingbox;
        this.ancestors = ancestors;
    }

    public Area(){

    }

    public float[] getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(float[] boundingbox) {
        this.boundingbox = boundingbox;
    }

    public List<String> getAncestors() {
        return ancestors;
    }

    public void setAncestors(List<String> ancestors) {
        this.ancestors = ancestors;
    }

    public abstract List<Place> getPlaces();

    public abstract void setPlaces(List<Place> places);
}
