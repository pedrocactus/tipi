package com.pedrocactus.topobloc.app.service;

import com.pedrocactus.topobloc.app.model.Route;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by pierrecastex on 23/11/2014.
 */
public interface TopoblocAPI {


    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("get/9")
    List<Route> getListRoutes();
/*
    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/movies/{id}.json")
    Movie getMovie(@Path("id") long movieId);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/movies/{id}/similar.json")
    MoviesWrapper getSimilarMovies(@Path("id") long movieId);*/
}
