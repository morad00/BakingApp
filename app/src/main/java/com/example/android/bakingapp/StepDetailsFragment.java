package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.pojo.StepModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mourad on 12/21/2017.
 */

public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener {
    @BindView(R.id.step_short_description)
    TextView step_short_description;
    @BindView(R.id.step_description)
    TextView step_description;
    @BindView(R.id.video_player_view)
    SimpleExoPlayerView videoPlayerView;
    @BindView(R.id.pb_buffering)
    ProgressBar pbBuffering;

    private StepModel stepModel;
    private SimpleExoPlayer mExoPlayer;
    private boolean playWhenReady;

    private PlaybackStateCompat.Builder mStateBuilder;
    private MediaSessionCompat mediaSessionCompat;
    private long currentVideoPosition;
    private String videoUrl;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public static StepDetailsFragment newInstance(StepModel step) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        playWhenReady = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);
        stepModel = getArguments().getParcelable("step");
        if (stepModel != null) {
            step_short_description.setText(stepModel.getmShortDescription());
            step_description.setText(stepModel.getmDescription());
            if ((stepModel.getmVideoUrl() == null || (stepModel.getmThumbanilUrl() == null))) {
                videoPlayerView.setVisibility(View.GONE);
            } else {
                if (stepModel.getmThumbanilUrl() != null
                        && stepModel.getmThumbanilUrl().length() >= 3
                        && !TextUtils.getExtension(stepModel.getmThumbanilUrl()).equalsIgnoreCase("mp4")) {
                    Picasso.with(getContext()).load(stepModel.getmThumbanilUrl());
                }

                if (stepModel.getmVideoUrl() != null && !stepModel.getmVideoUrl().equalsIgnoreCase("")) {
                    videoUrl = stepModel.getmVideoUrl();
                } else {
                    videoPlayerView.setVisibility(View.GONE);
                }
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("CurrentPosition", currentVideoPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void initializePlayer() {
        // intializing media session
        if (mediaSessionCompat == null) {
            mediaSessionCompat = new MediaSessionCompat(getActivity(), "Recipe");
            mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
            mediaSessionCompat.setMediaButtonReceiver(null);
            mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                    PlaybackStateCompat.ACTION_PLAY |
                            PlaybackStateCompat.ACTION_PAUSE |
                            PlaybackStateCompat.ACTION_PLAY_PAUSE |
                            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
            mediaSessionCompat.setPlaybackState(mStateBuilder.build());
            mediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {
                @Override
                public void onPlay() {
                    playWhenReady = true;
                }

                @Override
                public void onPause() {
                    playWhenReady = false;
                }

                @Override
                public void onSkipToPrevious() {
                    mExoPlayer.seekTo(0);
                }
            });
            mediaSessionCompat.setActive(true);
        }


        // intializing video player
        if (mExoPlayer == null && videoUrl != null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayer.seekTo(currentVideoPosition);
            videoPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getActivity(), "Recipe");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            currentVideoPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        if (mediaSessionCompat != null) {
            mediaSessionCompat.setActive(false);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
            pbBuffering.setVisibility(View.GONE);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
            pbBuffering.setVisibility(View.GONE);
        } else if (playbackState == ExoPlayer.STATE_BUFFERING) {
            pbBuffering.setVisibility(View.VISIBLE);
        } else {
            pbBuffering.setVisibility(View.GONE);
        }

        // pass to mediaSession
        mediaSessionCompat.setPlaybackState(mStateBuilder.build());

    }

    @Override
    public void onPlayerError(com.google.android.exoplayer2.ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

}
