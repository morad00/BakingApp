package com.example.android.bakingapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.bakingapp.pojo.IngredientModel;
import com.example.android.bakingapp.pojo.RecipeModel;
import com.example.android.bakingapp.pojo.StepModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mourad on 12/17/2017.
 */

public class MainAsyncTask extends AsyncTaskLoader<ArrayList<RecipeModel>> {
    private static final String API_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static ArrayList<RecipeModel> recipes;


    public MainAsyncTask(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<RecipeModel> loadInBackground() {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(API_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        ArrayList<RecipeModel> recipeModels = convertJSON(stringBuilder.toString());
        return recipeModels;
    }

    private ArrayList<RecipeModel> convertJSON(String strJson) {
        if (strJson == null) return null;
        recipes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeJson = jsonArray.getJSONObject(i);

                RecipeModel recipeModel = new RecipeModel();
                recipeModel.setmId(recipeJson.getInt("id"));
                recipeModel.setmName(recipeJson.getString("name"));

                ArrayList<IngredientModel> ingredientModels = new ArrayList<>();
                JSONArray ingredientJsonArray = recipeJson.getJSONArray("ingredients");
                for (int j = 0; j < ingredientJsonArray.length(); j++) {
                    JSONObject ingredientJson = ingredientJsonArray.getJSONObject(j);
                    IngredientModel ingredientModel = new IngredientModel();
                    ingredientModel.setmQuantity(ingredientJson.getDouble("quantity"));
                    ingredientModel.setmMeasure(ingredientJson.getString("measure"));
                    ingredientModel.setmIngredient(ingredientJson.getString("ingredient"));
                    ingredientModels.add(ingredientModel);
                }
                recipeModel.setmIngredientModels(ingredientModels);

                ArrayList<StepModel> stepModels = new ArrayList<>();
                JSONArray stepJsonArray = recipeJson.getJSONArray("steps");
                for (int j = 0; j < stepJsonArray.length(); j++) {
                    JSONObject stepJson = stepJsonArray.getJSONObject(j);
                    StepModel stepModel = new StepModel();
                    stepModel.setmId(stepJson.getInt("id"));
                    stepModel.setmShortDescription(stepJson.getString("shortDescription"));
                    stepModel.setmDescription(stepJson.getString("description"));
                    stepModel.setmVideoUrl(stepJson.getString("videoURL"));
                    stepModel.setmThumbanilUrl(stepJson.getString("thumbnailURL"));
                    stepModels.add(stepModel);
                }
                recipeModel.setmStepModels(stepModels);
                recipeModel.setServings(recipeJson.getInt("servings"));
                recipeModel.setImage(recipeJson.getString("image"));

                recipes.add(recipeModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    // to get ingredient list for widget
    public static ArrayList<RecipeModel> getRecipeModelsList() {
        return recipes;
    }

}

