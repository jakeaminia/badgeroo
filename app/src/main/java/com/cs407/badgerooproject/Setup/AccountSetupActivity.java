package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs407.badgerooproject.R;

// Fragments: UploadProfilePictureFragment, SetupPreferencesFragment

public class AccountSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);
    }
}