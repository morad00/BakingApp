package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.pojo.StepModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mourad on 12/21/2017.
 */

public class StepDetailActivity extends AppCompatActivity {

    @BindView(R.id.previous_step)
    TextView previousStep;
    @BindView(R.id.next_step)
    TextView nextStep;
    @BindView(R.id.navigation)
    LinearLayout llStepNavigation;
    @BindView(R.id.v_horizontal)
    View vHorizontal;

    private ArrayList<StepModel> stepModels = new ArrayList<>();
    private StepDetailsFragment stepDetailsFragment;
    private int stepIndex;
    private int orientation;
    private Bundle savedInstanceState;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra("steps") && getIntent().hasExtra("stepIndex")) {
            stepModels = getIntent().getParcelableArrayListExtra("steps");
            if (savedInstanceState == null) {
                stepIndex = getIntent().getIntExtra("stepIndex", 0);
            } else {
                stepIndex = savedInstanceState.getInt("stepIndex");
            }
            updateNavigation(stepIndex);
        }

        setFragment(savedInstanceState, stepIndex);
    }

    @OnClick(R.id.next_step)
    public void setNextStep() {
        stepIndex = stepIndex + 1;
        setFragment(null, stepIndex);
        updateNavigation(stepIndex);
    }

    @OnClick(R.id.previous_step)
    public void setPreviousStep() {
        stepIndex = stepIndex - 1;
        setFragment(null, stepIndex);
        updateNavigation(stepIndex);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (stepDetailsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "stepDetailFragment", stepDetailsFragment);
        }
        outState.putInt("currentStepIndex", stepIndex);
        super.onSaveInstanceState(outState);
    }

    private void setFragment(Bundle savedInstanceState, int stepIndex) {
        // setting toolbar first
        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(stepModels.get(stepIndex).getmShortDescription());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState != null) {
            stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().
                    getFragment(savedInstanceState, "stepDetailFragment");
        } else {
            stepDetailsFragment = StepDetailsFragment.newInstance(stepModels.get(stepIndex));
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, stepDetailsFragment)
                .commit();
    }

    private void updateNavigation(int stepIndex) {
        if (stepModels.size() > 1) {
            if (stepIndex == 0) {
                // first step in recipe
                previousStep.setVisibility(View.INVISIBLE);
                nextStep.setText(stepModels.get(stepIndex + 1).getmShortDescription());
            } else if (stepIndex == stepModels.size() - 1) {
                // last step in recipe
                nextStep.setVisibility(View.INVISIBLE);
                previousStep.setText(stepModels.get(stepIndex - 1).getmShortDescription());
            } else {
                // other steps in between
                nextStep.setVisibility(View.VISIBLE);
                previousStep.setVisibility(View.VISIBLE);
                nextStep.setText(stepModels.get(stepIndex + 1).getmShortDescription());
                previousStep.setText(stepModels.get(stepIndex - 1).getmShortDescription());
            }
        } else {
            // Only a one step
            previousStep.setVisibility(View.GONE);
            nextStep.setVisibility(View.GONE);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientation = newConfig.orientation;
        onWindowFocusChanged(true);
    }

}
