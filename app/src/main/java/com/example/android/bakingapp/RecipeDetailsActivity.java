package com.example.android.bakingapp;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.bakingapp.pojo.IngredientModel;
import com.example.android.bakingapp.pojo.RecipeModel;
import com.example.android.bakingapp.pojo.StepModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 12/17/2017.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements StepFragment.StepFragmentListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_container)
    ViewPager mViewPager;
    @BindView(R.id.btnAddWidget)
    Button btnAddWidget;

    FrameLayout flDetailStepContainer;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private IngredientFragment ingredientFragment;
    private StepFragment stepFragment;
    private RecipeModel recipeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().hasExtra("recipe")) {
            recipeModel = (RecipeModel) getIntent().getParcelableExtra("recipe");
        }

        flDetailStepContainer = (FrameLayout) findViewById(R.id.detail_step_container);
        if (getResources().getBoolean(R.bool.isTablet)) {
            ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
            layoutParams.width = (int) (7.0f / 18.0f * DisplayMetricUtils.getDeviceWidth(this));
            tabLayout.setLayoutParams(layoutParams);
        }

        // setting the Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipeModel.getmName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        // setting the Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            ingredientFragment = IngredientFragment.newInstance(recipeModel.getmIngredientModels());
            stepFragment = StepFragment.newInstance(recipeModel.getmStepModels());
        } else {
            ingredientFragment = (IngredientFragment) fragmentManager.getFragment(savedInstanceState, "ingredientFragment");
            stepFragment = (StepFragment) fragmentManager.getFragment(savedInstanceState, "stepFragment");
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager, ingredientFragment, stepFragment);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        btnAddWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWidgetScreen(MainAsyncTask.getRecipeModelsList().get(recipeModel.getmId() - 1).getmIngredientModels(), recipeModel);
                Toast.makeText(getApplicationContext(), "Ingredients Added to widget", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void updateWidgetScreen(List<IngredientModel> ingredientArrayList, RecipeModel recipeModel) {
        String ingredients = recipeModel.getmName() + "\n";
        int c = 0;
        for (IngredientModel i : ingredientArrayList) {
            c++;
            ingredients = ingredients
                    + c + ": "
                    + i.getmIngredient() + ": "
                    + i.getmQuantity() + " "
                    + i.getmMeasure() + "\n";
        }
        WidgetUpdateService.startWidgetUpdate(this, ingredients);
    }

    private RecipeClickListener recipeClickListener;

    public interface RecipeClickListener {
        void onRecipeItemClick();
    }


    private void showDetailStepFragment(StepModel step) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_step_container, StepDetailsFragment.newInstance(step))
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepClicked(StepModel step) {
        showDetailStepFragment(step);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private IngredientFragment ingredientFragment;
        private StepFragment stepFragment;

        public SectionsPagerAdapter(FragmentManager fm, IngredientFragment ingredientFragment, StepFragment stepFragment) {
            super(fm);
            this.ingredientFragment = ingredientFragment;
            this.stepFragment = stepFragment;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ingredientFragment;
                case 1:
                    return stepFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Steps";
            }
            return null;
        }
    }

}
