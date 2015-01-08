package com.pedrocactus.topobloc.app.model;

/**
 * Created by pierrecastex on 29/07/2014.
 */
public class Sector {
    private String Name;

    public void setName(String name) {
        Name = name;
    }

    public void setNumner(int numner) {
        this.numner = numner;
    }

    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sector() {
    }

    private int numner;
    private Route[] routes;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return Name;
    }

    public int getNumner() {
        return numner;
    }

    public Route[] getRoutes() {
        return routes;
    }

    private String description;
}
