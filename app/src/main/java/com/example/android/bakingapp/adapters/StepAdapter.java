package com.example.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.RecipeModel;
import com.example.android.bakingapp.pojo.StepModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 10/17/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    public static final String KEY_VEDIO = "vedio";
    public static final String KEY_RECIPE = "recipe";
    private RecipeModel mRecipeModel;

    public StepClickListener clickListener;
    private ArrayList<StepModel> steps;

    public StepAdapter(ArrayList<StepModel> steps, StepClickListener clickListener) {
        this.steps = steps;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final StepModel stepModel = steps.get(position);
        holder.mShortDesc.setText(stepModel.getmShortDescription());
        holder.bind(steps.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shortDesc)
        TextView mShortDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final StepModel stepModel, final StepClickListener clickListener) {
            mShortDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onStepItemClick(stepModel);
                }
            });
        }
    }

    public interface StepClickListener {
        void onStepItemClick(StepModel step);
    }

}
