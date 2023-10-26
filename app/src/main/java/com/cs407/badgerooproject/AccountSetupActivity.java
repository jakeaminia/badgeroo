package com.cs407.badgerooproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// Fragments: UploadProfilePictureFragment, SetupPreferencesFragment

public class AccountSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);
    }
}