package com.pedrocactus.topobloc.app.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route extends Place implements Parcelable{

    public String level;
    private boolean surplomb;
    private boolean devers;
    private boolean dalle;
    private boolean danger;
    private boolean highball;
    private boolean offshore;
    private int number;
    private int rating;
    private String circuit;




    public Route(){

    }
    public int describeContents() {return 0;}



    public Route(String level, boolean surplomb, boolean devers, boolean dalle,
                 boolean danger, boolean highball, boolean offshore, int number,
                 int rating, String circuit, String name, float[] coordinates,
                 List<String> images) {
        super(name,coordinates,images);
        this.level = level;
        this.surplomb = surplomb;
        this.devers = devers;
        this.dalle = dalle;
        this.danger = danger;
        this.highball = highball;
        this.offshore = offshore;
        this.number = number;
        this.rating = rating;
        this.circuit = circuit;
    }

    private Route(Parcel in) {
        this.level = in.readString();
        this.surplomb =  in.readByte() != 0;
        this.devers =  in.readByte() != 0;
        this.dalle =  in.readByte() != 0;
        this.danger =  in.readByte() != 0;
        this.highball =  in.readByte() != 0;
        this.offshore =  in.readByte() != 0;
        this.number = in.readInt();
        this.rating = in.readInt();
        this.circuit = in.readString();
        this.name = in.readString();
        in.readFloatArray(this.coordinates);
        /*year = in.readInt();
        synopsis = in.readString();
        actors = new ArrayList<Actor>();
        in.readTypedList(actors,Actor.CREATOR);
        critics = in.readString();
        posters = in.readParcelable(Posters.class.getClassLoader());
        releaseDates = in.readParcelable(ReleaseDates.class.getClassLoader());
        similarMovies = new ArrayList<Movie>();
        in.readTypedList(similarMovies,Movie.CREATOR);*/
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
    public static final Parcelable.Creator<Route> CREATOR
            = new Parcelable.Creator<Route>() {
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };


    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }




    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }




    /**
     * @return the surplomb
     */
    public boolean isSurplomb() {
        return surplomb;
    }




    /**
     * @param surplomb the surplomb to set
     */
    public void setSurplomb(boolean surplomb) {
        this.surplomb = surplomb;
    }




    /**
     * @return the devers
     */
    public boolean isDevers() {
        return devers;
    }




    /**
     * @param devers the devers to set
     */
    public void setDevers(boolean devers) {
        this.devers = devers;
    }




    /**
     * @return the dalle
     */
    public boolean isDalle() {
        return dalle;
    }




    /**
     * @param dalle the dalle to set
     */
    public void setDalle(boolean dalle) {
        this.dalle = dalle;
    }




    /**
     * @return the danger
     */
    public boolean isDanger() {
        return danger;
    }




    /**
     * @param danger the danger to set
     */
    public void setDanger(boolean danger) {
        this.danger = danger;
    }




    /**
     * @return the highball
     */
    public boolean isHighball() {
        return highball;
    }




    /**
     * @param highball the highball to set
     */
    public void setHighball(boolean highball) {
        this.highball = highball;
    }




    /**
     * @return the offshore
     */
    public boolean isOffshore() {
        return offshore;
    }




    /**
     * @param offshore the offshore to set
     */
    public void setOffshore(boolean offshore) {
        this.offshore = offshore;
    }




    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }




    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }




    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }




    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }




    /**
     * @return the circuit
     */
    public String getCircuit() {
        return circuit;
    }




    /**
     * @param circuit the circuit to set
     */
    public void setCircuit(String circuit) {
        this.circuit = circuit;
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




}
