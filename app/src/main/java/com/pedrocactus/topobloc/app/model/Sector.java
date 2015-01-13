package com.pedrocactus.topobloc.app.model;

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

    public Sector(String name, float[] coordinates, int number, List<Route> routes, String description) {
        super(name, coordinates);
        this.number = number;
        this.routes = routes;
        this.description = description;
    }

    private int number;
    private List<Route> routes;

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
