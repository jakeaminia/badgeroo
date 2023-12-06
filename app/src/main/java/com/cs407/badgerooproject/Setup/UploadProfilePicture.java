package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.cs407.badgerooproject.R;

public class UploadProfilePicture extends AppCompatActivity {

    ImageButton arrow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_picture);
        String email = getIntent().getStringExtra("email");
        arrow_btn = findViewById(R.id.arrow_btn_picture);

        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadProfilePicture.this, SetUpPreferences.class);
                startActivity(intent);
            }
        });
    }
}