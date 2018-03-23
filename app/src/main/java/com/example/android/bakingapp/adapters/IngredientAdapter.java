package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.IngredientModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 10/17/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<IngredientModel> mIngredientModels;
    private Context context;

    public IngredientAdapter(ArrayList<IngredientModel> ingredients) {
        this.mIngredientModels = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        if (context == null) {
            context = row.getContext();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IngredientModel ingredientModel = mIngredientModels.get(position);
        holder.tvIngredient.setText(ingredientModel.getmIngredient());
        holder.tvQuantity.setText(String.valueOf(ingredientModel.getmQuantity()) + " " +
                ingredientModel.getmMeasure());
    }

    @Override
    public int getItemCount() {
        return mIngredientModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient)
        TextView tvIngredient;
        @BindView(R.id.quantity)
        TextView tvQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
