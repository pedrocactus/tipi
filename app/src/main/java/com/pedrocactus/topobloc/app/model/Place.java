package com.pedrocactus.topobloc.app.model;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pierrecastex on 12/01/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Place  implements Parcelable{

    public String name;
    public float[] coordinates;
    public List<String> images;

    public Place(String name, float[] coordinates,List<String> images) {
        this.name = name;
        this.coordinates = coordinates;
        this.images = images;
    }

    public Place() {
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
