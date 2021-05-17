package com.example.peerchat.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.peerchat.R;

public class ProfileUpdate extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        this.setTitle("Profile Settings");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }
}