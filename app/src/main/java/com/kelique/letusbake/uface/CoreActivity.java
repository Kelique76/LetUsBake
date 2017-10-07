package com.kelique.letusbake.uface;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kelique.letusbake.R;

/**
 * Created by kelique on 8/29/2017.
 */

public abstract class CoreActivity extends AppCompatActivity{
    protected abstract Fragment buatFragment();

    @LayoutRes
    protected int getLayoutResId() {return R.layout.activity_base;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager FgMgr = getSupportFragmentManager();
        Fragment fragment = FgMgr.findFragmentById(R.id.fragment_container);
        if (fragment==null){
            fragment = buatFragment();
            FgMgr.beginTransaction()
                    .add(R.id.fragment_container, fragment, "RecipeListFragment")
                    .commit();
        }
    }
}


