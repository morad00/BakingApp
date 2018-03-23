package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.StepAdapter;
import com.example.android.bakingapp.pojo.StepModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 12/21/2017.
 */

public class StepFragment extends Fragment implements StepAdapter.StepClickListener {
    @BindView(R.id.rv_list_steps)
    RecyclerView rvStep;


    private StepAdapter stepAdapter;
    private ArrayList<StepModel> steps = new ArrayList<>();
    private int stepIndex;
    private StepFragmentListener fragmentListener;

    @Override
    public void onStepItemClick(StepModel step) {
        stepIndex = steps.indexOf(step);
        if (getResources().getBoolean(R.bool.isTablet)) {
            fragmentListener.onStepClicked(step);
        } else {
            Intent intent = new Intent(getActivity(), StepDetailActivity.class);
            intent.putParcelableArrayListExtra("steps", steps);
            intent.putExtra("stepIndex", stepIndex);
            startActivity(intent);
        }
    }

    public interface StepFragmentListener {
        void onStepClicked(StepModel step);
    }

    public static StepFragment newInstance(ArrayList<StepModel> steps) {
        StepFragment stepFragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", steps);
        stepFragment.setArguments(bundle);
        return stepFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            steps = getArguments().getParcelableArrayList("steps");
        }
        // setRecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvStep.setLayoutManager(layoutManager);
        stepAdapter = new StepAdapter(steps, this);
        rvStep.setAdapter(stepAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentListener = (StepFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public StepFragment() {
        // Required empty public constructor
    }

}
