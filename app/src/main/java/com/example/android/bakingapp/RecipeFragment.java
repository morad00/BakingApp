package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.pojo.RecipeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 12/17/2017.
 */

public class RecipeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<RecipeModel>> {
    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;


    private RecipeAdapter recipeAdapter;
    private static final int NEW = 0;
    private ArrayList<RecipeModel> recipes;
    private int state;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(long recipeId) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("recipeId", recipeId);
        recipeFragment.setArguments(bundle);
        return recipeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("state", state);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, row);
        setRecyclerView();
        return row;
    }

    private void setRecyclerView() {
        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipes = new ArrayList<>();

        //Portrait mode, both phone and tablet
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rvRecipes.setLayoutManager(layoutManager);
        }

        //Landscape mode (Tablet)
        else if (getResources().getBoolean(R.bool.isTablet)) {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            rvRecipes.setLayoutManager(layoutManager);
        }
        //Landscape mode (phone)
        else {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            rvRecipes.setLayoutManager(layoutManager);
        }
        recipeAdapter = new RecipeAdapter(getActivity().getApplicationContext(), recipes, new RecipeAdapter.RecipeClickListener() {
            @Override
            public void onRecipeItemClick(RecipeModel recipeModel) {
                Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
                intent.putExtra("recipe", recipeModel);
                startActivity(intent);
            }
        });
        recipeAdapter.notifyDataSetChanged();
        rvRecipes.setAdapter(recipeAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).startLoading();
        getLoaderManager().restartLoader(NEW, null, this);
        Snackbar.make(rvRecipes, R.string.loading, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // for AsyncLoader
    @Override
    public Loader<ArrayList<RecipeModel>> onCreateLoader(int id, Bundle args) {
        return new MainAsyncTask(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RecipeModel>> loader, ArrayList<RecipeModel> data) {
        if (data == null || data.size() == 0) {
            Snackbar.make(rvRecipes, R.string.internet_error, Snackbar.LENGTH_INDEFINITE).show();
            return;
        }
//        Log.v("testOnLoadFinished", data.get(0).getmName());
        recipeAdapter.setRecipes(data);
        Snackbar.make(rvRecipes, R.string.success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RecipeModel>> loader) {
    }


}
