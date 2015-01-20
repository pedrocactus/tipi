package com.pedrocactus.topobloc.app.model;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pierrecastex on 12/01/2015.
 */
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = NationalSite.class, name = "nationalsite"),
//        @JsonSubTypes.Type(value = Site.class, name = "site"),
//        @JsonSubTypes.Type(value = Sector.class, name = "sector"),
//        @JsonSubTypes.Type(value = Route.class, name = "route")
//})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Place  implements Parcelable{

    public String name;
    public float[] coordinates;
    public float[] boundingbox;
    public List<String> images;

    public Place(String name, float[] coordinates,List<String> images, float[] boundingbox) {
        this.name = name;
        this.coordinates = coordinates;
        this.images = images;
        this.boundingbox = boundingbox;
    }

    public float[] getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(float[] boundingbox) {
        this.boundingbox = boundingbox;
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
