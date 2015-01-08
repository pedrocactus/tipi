package com.pedrocactus.topobloc.app.events;

import com.pedrocactus.topobloc.app.model.Route;

import java.util.List;

/**
 * Created by castex on 08/01/15.
 */
public class FetchRouteListEvent {
    List<Route> routes;
    public List<Route> getMovies() {
        return routes;
    }
    public void setMovies(List<Route> routes) {
        this.routes = routes;
    }
    public FetchRouteListEvent(List<Route> routes) {
        this.routes = routes;
    }
}
