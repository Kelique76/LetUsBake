package com.kelique.letusbake.uface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kelique.letusbake.R;
import com.kelique.letusbake.uface.fragment.RecipeDetailFragment;
import com.kelique.letusbake.uface.fragment.StepFragment;
import com.kelique.letusbake.model.Recipe;
import com.kelique.letusbake.model.Step;
import com.kelique.letusbake.uface.presenter.RecipeDetailPresenter;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailPresenter.Callbacks {
    private static final String BUNDLE_RECIPE_DATA =
            "com.kelique.letusbake.recipe_data";

    public static Intent newIntent(Context packageContext, Recipe recipe) {
        Intent intent = new Intent(packageContext, RecipeDetailActivity.class);
        intent.putExtra(BUNDLE_RECIPE_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Recipe recipe = getIntent().getExtras().getParcelable(BUNDLE_RECIPE_DATA);

        if (fragment == null) {
            fragment = RecipeDetailFragment.newInstance(recipe);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();

            if (getResources().getBoolean(R.bool.isTablet)) {
                Fragment newDetail = StepFragment.newInstance(recipe.getSteps().get(0));
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
            }
        }
    }

    @Override
    public void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = StepActivity.newIntent(this, stepList, currentStep, recipeName);
            startActivity(intent);
        } else {
            Fragment newDetail = StepFragment.newInstance(stepList.get(currentStep));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }


//    @Override
//    public void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName) {
//        if (!getResources().getBoolean(R.bool.isTablet)) {
//            Intent intent = StepActivity.newIntent(this, stepList, currentStep, recipeName);
//            startActivity(intent);
//        } else {
//            Fragment newDetail = StepFragment.newInstance(stepList.get(currentStep));
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.detail_fragment_container, newDetail)
//                    .commit();
//        }
//    }
}
