package com.pedrocactus.topobloc.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

/**
 * Created by pierrecastex on 12/01/2015.
 */
//@JsonTypeName("nationalsite")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NationalSite extends Area{


    public List<Site> sites;
    public String name;



    public NationalSite(){

    }

    public NationalSite(List<Site> sites, String name, String description,String history,
                List<String> images, float[] coordinates, float[] boundingBox,List<String> ancestors,float[] parentboundingbox) {
        super(name, coordinates,images,boundingBox,description,history,ancestors,parentboundingbox);
        this.sites = sites;
        this.name = name;
        this.images = images;
    }

    private NationalSite(Parcel in) {

    }

    public int describeContents() {return 0;}
    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
    public static final Parcelable.Creator<NationalSite> CREATOR
            = new Parcelable.Creator<NationalSite>() {
        public NationalSite createFromParcel(Parcel in) {
            return new NationalSite(in);
        }
        public NationalSite[] newArray(int size) {
            return new NationalSite[size];
        }
    };

    /**
     * @return the sites
     */
    public List<Site> getSites() {
        return sites;
    }


    /**
     * @param sites the sites to set
     */
    public void setSites(List<Site> sites) {
        this.sites = sites;
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


    public List<Place> getPlaces(){
        return (List<Place>)(List<?>)sites;
    }

    public void setPlaces(List<Place> places){
        sites = (List<Site>)(List<?>)places;
    }

}
