package com.example.android.bakingapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mourad on 10/17/2017.
 */

public class RecipeModel implements Parcelable {
    private int mId;
    private String mName;
    private ArrayList<IngredientModel> mIngredientModels;
    private ArrayList<StepModel> mStepModels;
    private int servings;
    private String image;

    public RecipeModel() {
    }

    private RecipeModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredientModels = in.createTypedArrayList(IngredientModel.CREATOR);
        mStepModels = in.createTypedArrayList(StepModel.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<IngredientModel> getmIngredientModels() {
        return mIngredientModels;
    }

    public void setmIngredientModels(ArrayList<IngredientModel> mIngredientModels) {
        this.mIngredientModels = mIngredientModels;
    }

    public ArrayList<StepModel> getmStepModels() {
        return mStepModels;
    }

    public void setmStepModels(ArrayList<StepModel> mStepModels) {
        this.mStepModels = mStepModels;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeTypedList(mIngredientModels);
        parcel.writeTypedList(mStepModels);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
