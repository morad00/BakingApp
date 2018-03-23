package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.MainAsyncTask;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeActivity;
import com.example.android.bakingapp.pojo.RecipeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 10/17/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private RecipeClickListener listener;
    private ArrayList<RecipeModel> recipes;
    private Context mContext;

    public RecipeAdapter(Context mContext, ArrayList<RecipeModel> recipes, RecipeClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
        mContext = mContext;
    }

    public void setRecipes(ArrayList<RecipeModel> mRecipes) {
        recipes.clear();
        recipes.addAll(mRecipes);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final RecipeModel recipeModel = recipes.get(position);
        holder.tvTitle.setText(recipeModel.getmName());
        holder.tvServing.setText("servings :" + recipeModel.getServings());
//        if (recipeModel.getImage().length() != 0)
//            Picasso.with(mContext).load(recipeModel.getImage()).into(holder.imageView);
//        else {
//            switch (position) {
//                case 0:
//                    Picasso.with(mContext).load(R.drawable.nutella).into(holder.imageView);
//                case 1:
//                    Picasso.with(mContext).load(R.drawable.brownies).into(holder.imageView);
//                case 2:
//                    Picasso.with(mContext).load(R.drawable.yellowcake).into(holder.imageView);
//                case 3:
//                    Picasso.with(mContext).load(R.drawable.cheesecake).into(holder.imageView);
//            }
//        }
        holder.bind(recipes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView tvTitle;
        @BindView(R.id.servings)
        TextView tvServing;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final RecipeModel recipeModel, final RecipeClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecipeItemClick(recipeModel);
                }
            });
        }
    }

    public interface RecipeClickListener {
        void onRecipeItemClick(RecipeModel review);
    }
}
