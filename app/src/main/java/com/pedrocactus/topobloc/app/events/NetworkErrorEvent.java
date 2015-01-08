package com.pedrocactus.topobloc.app.events;

/**
 * Created by castex on 08/01/15.
 */
public class NetworkErrorEvent {
    public static int CACHE=0;
    public static int OTHER=1;
    private int errorType;
    public int getErrorType() {
        return errorType;
    }
    public NetworkErrorEvent(int errorType) {
        this.errorType = errorType;
    }
}
