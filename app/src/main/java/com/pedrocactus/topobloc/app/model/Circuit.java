package com.pedrocactus.topobloc.app.model;

/**
 * Created by pierrecastex on 29/03/2015.
 */
public enum Circuit {

    RED("red"),YELLOW("yellow"),ORANGE("orange"),BLUE("blue"),WHITE("white"),BLACK("black");


    private String color = "";

    Circuit(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
