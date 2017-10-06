package com.kelique.letusbake.networking;

import com.kelique.letusbake.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UdApiSvc {
    @GET(" ")
    Observable<ArrayList<Recipe>> getUdacityRecipeResult();
}
