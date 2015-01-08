package com.pedrocactus.topobloc.app.model;

import java.util.List;

/**
 * Created by pierrecastex on 25/11/2014.
 */
public class Site {


    public List<Sector> sectors;
    public String name;
    public String description;
    public List<String> images;

    public float[] coordinates;


    public Site(){

    }


    public Site(List<Sector> sectors, String name, String description,
                List<String> images, float[] coordinates) {
        super();
        this.sectors = sectors;
        this.name = name;
        this.description = description;
        this.images = images;
        this.coordinates = coordinates;
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
