package com.example.android.bakingapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Mourad on 10/17/2017.
 */

public class StepModel implements Parcelable {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbanilUrl;

    public StepModel() {
    }

    protected StepModel(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbanilUrl = in.readString();
    }

    public static final Creator<StepModel> CREATOR = new Creator<StepModel>() {
        @Override
        public StepModel createFromParcel(Parcel in) {
            return new StepModel(in);
        }

        @Override
        public StepModel[] newArray(int size) {
            return new StepModel[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getmThumbanilUrl() {
        return mThumbanilUrl;
    }

    public void setmThumbanilUrl(String mThumbanilUrl) {
        this.mThumbanilUrl = mThumbanilUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbanilUrl);
    }
}

