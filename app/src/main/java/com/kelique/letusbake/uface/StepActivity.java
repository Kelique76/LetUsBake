package com.kelique.letusbake.uface;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelique.letusbake.R;
import com.kelique.letusbake.databinding.ActivityStepViewpagerBinding;
import com.kelique.letusbake.model.Step;
import com.kelique.letusbake.uface.fragment.StepFragment;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {
    private static final String BUNDLE_STEP_DATA =
            "com.kelique.letusbake.step_data";
    private static final String BUNDLE_CURRENT_RECIPE =
            "com.kelique.letusbake.current_recipe";
    private static final String BUNDLE_CURRENT_STEP =
            "com.kelique.letusbake.current_step";
    private ViewPager mViewPager;

    private ActivityStepViewpagerBinding binding;


    //enabling backpress on Navigation Up
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    public static Intent newIntent(Context packageContext, ArrayList<Step> stepList,
                                   int currentStep, String recipeName) {
        Intent intent = new Intent(packageContext, StepActivity.class);
        intent.putExtra(BUNDLE_STEP_DATA, stepList);
        intent.putExtra(BUNDLE_CURRENT_STEP, currentStep);
        intent.putExtra(BUNDLE_CURRENT_RECIPE, recipeName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_viewpager);

        //to call actionbar and show it in menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Step> stepList = getIntent().getExtras().getParcelableArrayList(BUNDLE_STEP_DATA);
        final int currentStep = getIntent().getExtras().getInt(BUNDLE_CURRENT_STEP);
        String currentRecipeName = getIntent().getExtras().getString(BUNDLE_CURRENT_RECIPE);

        setSupportActionBar(binding.tbToolbar.toolbar);
        binding.tbToolbar.toolbar.setTitle(currentRecipeName);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_activity_step_viewpager);
        for(Step step : stepList) {
            tabLayout.addTab(tabLayout.newTab().setText(
                    String.format(getString(R.string.step_number_format), (step.getId() + 1))));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //Fullscreen mode for non-tablet landscape orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.tbToolbar.toolbar.setVisibility(View.GONE);
            binding.tlActivityStepViewpager.setVisibility(View.GONE);
        }

        mViewPager = (ViewPager) findViewById(R.id.vp_activity_step_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
                                  @Override
                                  public Fragment getItem(int position) {
                                      return StepFragment.newInstance(stepList.get(position));
                                  }
                                  @Override
                                  public int getCount() {
                                      return stepList.size();
                                  }
                              });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mViewPager.setCurrentItem(currentStep);
    }
}
