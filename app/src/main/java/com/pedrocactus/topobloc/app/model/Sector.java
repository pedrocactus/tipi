package com.pedrocactus.topobloc.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pierrecastex on 29/07/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sector extends Place{


    public void setNumber(int number) {
        this.number = number;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sector() {
    }


    private Sector(Parcel in) {

    }
    public int describeContents() {return 0;}
    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
    public static final Parcelable.Creator<Sector> CREATOR
            = new Parcelable.Creator<Sector>() {
        public Sector createFromParcel(Parcel in) {
            return new Sector(in);
        }
        public Sector[] newArray(int size) {
            return new Sector[size];
        }
    };

    public Sector(String name, float[] coordinates, int number, List<Route> routes, String description,List<String> images) {
        super(name, coordinates,images);
        this.number = number;
        this.routes = routes;
        this.description = description;
    }

    private int number;
    private List<Route> routes;
    private List<String> images;

    public String getDescription() {
        return description;
    }


    public int getNumber() {
        return number;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    private String description;
}
