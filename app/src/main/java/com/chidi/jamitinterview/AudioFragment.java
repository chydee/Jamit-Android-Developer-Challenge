package com.chidi.jamitinterview;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;


public class AudioFragment extends Fragment {

    private static final String audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3";

    private MediaPlayer player;

    private MaterialButton pausePlayButton;
    private TextView statusText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        pausePlayButton = view.findViewById(R.id.playPauseBtn);
        statusText = view.findViewById(R.id.audioStatusText);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        player = new MediaPlayer();
        setupPlayer();
        pausePlayButton.setOnClickListener(v -> {
            if (pausePlayButton.getText() == getString(R.string.play)) {
                player.start();
            } else {
                pausePlayMechanism();
            }
        });

        onAudioCompleted();
    }

    private void setupPlayer() {
        player.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        try {
            player.setDataSource(audioUrl);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
        statusText.setText(getString(R.string.audio_status_text_playing, "Playing"));
    }

    /**
     * Handles the Play and Pause
     * behaviour with just one button
     */
    private void pausePlayMechanism() {
        if (player.isPlaying()) {
            player.pause();
            pausePlayButton.setText(getString(R.string.resume));
            statusText.setText(getString(R.string.audio_status_text_playing, "Paused"));
        } else {
            player.start();
            pausePlayButton.setText(getString(R.string.pause));
            statusText.setText(getString(R.string.audio_status_text_playing, "Playing"));
        }
    }

    /**
     * Call this method to listen to when the audio has finished playing
     * and then notify user with a Toast that audio has finished playing
     * and resets the button to default state
     */
    private void onAudioCompleted() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                showMessage();
                resetButton();
            }
        });
    }

    private void resetButton() {
        pausePlayButton.setText(getString(R.string.play));
    }

    private void showMessage() {
        Toast.makeText(getContext(), "Audio has finished playing", Toast.LENGTH_SHORT).show();
        statusText.setText(getString(R.string.audio_status_text_stopped));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        player.release();
        player = null;
    }
}