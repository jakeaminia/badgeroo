package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs407.badgerooproject.R;

// Fragments: None

public class RegisterActivity extends AppCompatActivity {
    Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_btn = findViewById(R.id.register_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, UploadProfilePicture.class);
                startActivity(intent);
            }
        });

    }
}