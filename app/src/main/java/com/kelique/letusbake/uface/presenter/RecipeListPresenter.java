package com.kelique.letusbake.uface.presenter;

import android.view.View;

import com.kelique.letusbake.R;
import com.kelique.letusbake.model.Recipe;
import com.kelique.letusbake.networking.UdaNetService;

import java.util.ArrayList;
import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//import java.util.ArrayList;

/**
 * Created by kelique on 8/30/2017.
 */

public class RecipeListPresenter implements RecipeListPresenterViewContract.Presenter {
    private final RecipeListPresenterViewContract.View mView;
    private final UdaNetService mUdaNetService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public interface Callbacks {
        void recipeSelected(Recipe recipe);
    }

    public RecipeListPresenter(RecipeListPresenterViewContract.View view,
                               UdaNetService udaNetService) {
        this.mView = view;
        this.mUdaNetService = udaNetService;
    }

    @Override
    public void fetchRecipes() {
        Observable<ArrayList<Recipe>> retrofitObserver;

        retrofitObserver = this.mUdaNetService.networkApiRequestRecipes();

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkApiRecipeObserver());
    }

    private Observer<ArrayList<Recipe>> networkApiRecipeObserver() {
        return new Observer<ArrayList<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }
            @Override
            public void onNext(ArrayList<Recipe> networkRecipeResult) {
                ArrayList<Recipe> recipeList = new ArrayList<>();
                recipeList.addAll(networkRecipeResult);
                if(mView.isActive()) {
                    mView.updateAdapter(recipeList);
                }
            }
            @Override
            public void onError(Throwable e) {
                if(mView.isActive()) {
                    mView.displaySnackbarMessage(R.string.network_error_recipes);
                }
            }
            @Override
            public void onComplete() {}
        };
    }

    @Override
    public void recipeClicked(Recipe recipe, View view) {
        ((Callbacks) view.getContext()).recipeSelected(recipe);
    }

    @Override
    public void viewDestroy() {
        mCompositeDisposable.clear();
    }
}