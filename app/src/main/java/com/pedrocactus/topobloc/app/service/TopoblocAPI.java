package com.pedrocactus.topobloc.app.service;

import com.pedrocactus.topobloc.app.model.NationalSite;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.model.Sector;
import com.pedrocactus.topobloc.app.model.Site;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by pierrecastex on 23/11/2014.
 */
public interface TopoblocAPI {


    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/getRoute")
    List<Route> getRoutesFromSector(@Query("name") String sectorName);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/getNationalSites")
    List<NationalSite> getNationalSites();

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/getSite")
    List<Sector> getSectorsFromSite(@Query("siteName") String siteName);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/getSite")
    List<Site> getSitesFromNational(@Query("nationalSiteName") String siteName);
/*
    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/movies/{id}.json")
    Movie getMovie(@Path("id") long movieId);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/movies/{id}/similar.json")
    MoviesWrapper getSimilarMovies(@Path("id") long movieId);*/
}
