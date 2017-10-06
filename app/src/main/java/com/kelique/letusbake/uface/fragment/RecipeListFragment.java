package com.kelique.letusbake.uface.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelique.letusbake.R;
import com.kelique.letusbake.uface.adapter.RecipeAdapter;
import com.kelique.letusbake.databinding.FragmentRecipeListBinding;
import com.kelique.letusbake.model.Recipe;
import com.kelique.letusbake.networking.UdaNetService;
import com.kelique.letusbake.uface.presenter.RecipeListPresenter;
import com.kelique.letusbake.uface.presenter.RecipeListPresenterViewContract;
import com.kelique.letusbake.util.OnItemClickListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment
        implements RecipeListPresenterViewContract.View {
    private final String INSTANCE_KEY_RECIPE_LIST = "instance_key_recipe_list";

    private FragmentRecipeListBinding binding;
    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;
    private ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private RecipeListPresenter mRecipeListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(INSTANCE_KEY_RECIPE_LIST)) {
                mRecipeList = savedInstanceState.getParcelableArrayList(INSTANCE_KEY_RECIPE_LIST);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);
        final View view = binding.getRoot();
        binding.tbToolbar.toolbar.setTitle(getContext().getResources().getString(R.string.app_name));

        mRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.recipe_list_columns)));

        mRecipeListPresenter = new RecipeListPresenter(this, new UdaNetService());

        mAdapter = new RecipeAdapter(mRecipeList, new OnItemClickListener<Recipe>() {
            @Override
            public void onClick(Recipe recipe, View view) {
                mRecipeListPresenter.recipeClicked(recipe, view);
            }
        });
        mRecipeRecyclerView.setAdapter(mAdapter);

        //TODO:swipe to force refresh?
        if(mRecipeList==null || mRecipeList.size()==0) {
            mRecipeListPresenter.fetchRecipes();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecipeListPresenter.viewDestroy();
    }

    @Override
    public void updateAdapter(ArrayList<Recipe> recipeList) {
        mRecipeList.clear();
        mRecipeList.addAll(recipeList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displaySnackbarMessage(int stringResId) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.cl_list_container),
                getString(stringResId),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INSTANCE_KEY_RECIPE_LIST, mRecipeList);
        super.onSaveInstanceState(outState);
    }
}
