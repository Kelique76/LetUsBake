package com.kelique.letusbake.networking;

import com.kelique.letusbake.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;

public class UdaNetService {

    private UdApiSvc mUdacityApiObservable;

    public UdaNetService(){
        mUdacityApiObservable = ApInject.getUdacityApiObservable();
    }

    public Observable<ArrayList<Recipe>> networkApiRequestRecipes() {
        Observable observer = mUdacityApiObservable.getUdacityRecipeResult();
        return observer;
    }
}
