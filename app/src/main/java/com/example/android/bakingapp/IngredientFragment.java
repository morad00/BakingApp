package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.IngredientAdapter;
import com.example.android.bakingapp.pojo.IngredientModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 12/17/2017.
 */
public class IngredientFragment extends Fragment {

    private IngredientAdapter ingredientAdapter;
    @BindView(R.id.rv_ingredient)
    RecyclerView rvIngredient;

    public IngredientFragment() {
        // Required empty public constructor
    }

    public static IngredientFragment newInstance(ArrayList<IngredientModel> ingredients) {
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", ingredients);
        ingredientFragment.setArguments(bundle);
        return ingredientFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvIngredient.setLayoutManager(layoutManager);
        if (getArguments() != null) {
            ArrayList<IngredientModel> ingredients = getArguments().getParcelableArrayList("ingredients");
//            Log.v("firstIngredient", ingredients.get(0).getmIngredient());
            ingredientAdapter = new IngredientAdapter(ingredients);
            rvIngredient.setAdapter(ingredientAdapter);
        }
        return view;
    }


}
