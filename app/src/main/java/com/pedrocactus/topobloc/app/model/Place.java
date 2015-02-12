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
    public String description;
    public String history;
    public List<String> images;

    public Place(String name, float[] coordinates,List<String> images,String description,String history) {
        this.name = name;
        this.coordinates = coordinates;
        this.images = images;
        this.history = history;
        this.description = description;
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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


}
