package com.kelique.letusbake.networking;

public class ApInject {

    private ApInject(){}

    //Udacity Recipes
    public static final String UDACITY_BASE_URL_MOVIE = "http://go.udacity.com/android-baking-app-json/";

    public static UdApiSvc getUdacityApiObservable() {
        return RetrofitClient.getUdacityClient(UDACITY_BASE_URL_MOVIE).create(UdApiSvc.class);
    }

}
