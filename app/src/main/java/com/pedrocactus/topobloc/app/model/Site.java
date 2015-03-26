package com.pedrocactus.topobloc.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pierrecastex on 25/11/2014.
 */
public class Site extends Area{


    public List<Sector> sectors;
    public String name;
    public String description;



    public Site(){

    }


    public Site(List<Sector> sectors, String name,
                List<String> images, float[] coordinates, float[] boundingBox,String description,String history,List<String> ancestors,float[] parentboundingbox) {
        super(name, coordinates,images,boundingBox,description,history,ancestors,parentboundingbox);
        this.sectors = sectors;
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
    }

    private Site(Parcel in) {

    }
    public int describeContents() {return 0;}
    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
    public static final Parcelable.Creator<Site> CREATOR
            = new Parcelable.Creator<Site>() {
        public Site createFromParcel(Parcel in) {
            return new Site(in);
        }
        public Site[] newArray(int size) {
            return new Site[size];
        }
    };
    /**
     * @return the sites
     */
    public List<Sector> getSectors() {
        return sectors;
    }


    /**
     * @param sectors the sites to set
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
        return (List<Place>)(List<?>)sectors;
    }

    public void setPlaces(List<Place> places){
        sectors = (List<Sector>)(List<?>)places;
    }

}
