package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mourad on 12/17/2017.
 */

public class RecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        RecipeFragment recipeFragment = RecipeFragment.newInstance(getIntent().getLongExtra("recipeId", 0L));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_recipe, recipeFragment).commit();
    }
}
