package com.pedrocactus.topobloc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pierrecastex on 12/01/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NationalSite extends Place{


    public List<Sector> sectors;
    public String name;
    public String description;
    public List<String> images;



    public NationalSite(){

    }


    public NationalSite(List<Sector> sectors, String name, String description,
                List<String> images, float[] coordinates) {
        super(name, coordinates);
        this.sectors = sectors;
        this.name = name;
        this.description = description;
        this.images = images;
    }


    /**
     * @return the sectors
     */
    public List<Sector> getSectors() {
        return sectors;
    }


    /**
     * @param sectors the sectors to set
     */
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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


    /**
     * @return the images
     */
    public List<String> getImages() {
        return images;
    }


    /**
     * @param images the images to set
     */
    public void setImages(List<String> images) {
        this.images = images;
    }


    /**
     * @return the coordinates
     */
    public float[] getCoordinates() {
        return coordinates;
    }


    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

}
