package com.kelique.letusbake.uface.presenter;

import android.view.View;

import com.kelique.letusbake.model.Step;

import java.util.ArrayList;

public class RecipeDetailPresenter implements RecipeDetailPresenterViewContract.Presenter {

    private final RecipeDetailPresenterViewContract.View mView;

    public interface Callbacks {
        void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName);
    }

    public RecipeDetailPresenter(RecipeDetailPresenterViewContract.View view) {
        this.mView = view;
    }

    @Override
    public void stepClicked(ArrayList<Step> stepList, int currentStep, String recipeName, View view) {
        ((Callbacks) view.getContext()).stepSelected(stepList, currentStep, recipeName);
    }
}

