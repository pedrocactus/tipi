package com.pedrocactus.topobloc.app.model;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
    public float[] boundingBox;
    public List<String> images;

    public Place(String name, float[] coordinates,List<String> images, float[] boundingBox) {
        this.name = name;
        this.coordinates = coordinates;
        this.images = images;
        this.boundingBox = boundingBox;
    }

    public float[] getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(float[] boundingBox) {
        this.boundingBox = boundingBox;
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
