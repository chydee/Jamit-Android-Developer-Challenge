package com.chidi.jamitinterview;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class WelcomeFragment extends Fragment {

    private MaterialButton playAudioBtn;
    private SwitchMaterial themeToggle;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        playAudioBtn = view.findViewById(R.id.btnPlayAudio);
        themeToggle = view.findViewById(R.id.themeToggle);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSharePref();

        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if(isDarkModeOn){
            themeToggle.setText(getString(R.string.theme_night));
            themeToggle.setChecked(true);
        } else {
            themeToggle.setText(getString(R.string.theme_day));
            themeToggle.setChecked(false);
        }

        playAudioBtn.setOnClickListener(v -> {
            // Navigate to the Audio Player Screen
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_audioFragment);
        });

        themeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                setNightMode();
                buttonView.setText(getString(R.string.theme_night));
            } else {
                setDayMode();
                buttonView.setText(getString(R.string.theme_day));
            }
        });;
    }

    /**
     * Set up SharedPreference for managing app's theme
     * state
     */
    private void setupSharePref(){
         sharedPreferences = getActivity().getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
          editor = sharedPreferences.edit();
    }

    private void setNightMode(){
        AppCompatDelegate
                .setDefaultNightMode(
                        AppCompatDelegate
                                .MODE_NIGHT_YES);

        // it will set isDarkModeOn
        // boolean to true
        editor.putBoolean(
                "isDarkModeOn", true);
        editor.apply();
    }

    private void setDayMode(){
        AppCompatDelegate
                .setDefaultNightMode(
                        AppCompatDelegate
                                .MODE_NIGHT_NO);
        // it will set isDarkModeOn
        // boolean to false
        editor.putBoolean(
                "isDarkModeOn", false);
        editor.apply();

    }
}