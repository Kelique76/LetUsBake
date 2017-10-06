package com.kelique.letusbake.uface;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.kelique.letusbake.CoreActivity;
import com.kelique.letusbake.uface.fragment.RecipeListFragment;
import com.kelique.letusbake.model.Recipe;
import com.kelique.letusbake.uface.presenter.RecipeListPresenter;
import com.kelique.letusbake.widget.RecipeWidgetService;

public class MainActivity extends CoreActivity implements RecipeListPresenter.Callbacks {
    @Override
    protected Fragment buatFragment() {
        return new RecipeListFragment();
    }

    @Override
    public void recipeSelected(Recipe recipe) {
        Intent intet = RecipeDetailActivity.newIntent(this, recipe);
        RecipeWidgetService.startActionUpdateRecipeWidgets(this, recipe);
        startActivity(intet);

    }
//
//    @Override
//    protected Fragment createFragment (){return new RecipeListFragment()};
//
//
//    @Override
//    protected void RecipeSelected (Recipe recipe) {
//        Intent intet = RecipeDetailActivity.newIntent(this, recipe);
//        RecipeWidgetService.startActionUpdateRecipeWidgets(this, recipe);
//        startActivity(intet);
//    }
}
