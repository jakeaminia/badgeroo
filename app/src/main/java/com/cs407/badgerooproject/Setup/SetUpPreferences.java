package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.cs407.badgerooproject.Home.FindRoommatesActivity;
import com.cs407.badgerooproject.R;

public class SetUpPreferences extends AppCompatActivity {
    ImageButton arrow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_preferences);
        arrow_btn = findViewById(R.id.arrow_btn_preferences);
        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetUpPreferences.this, FindRoommatesActivity.class);
                startActivity(intent);
            }
        });
    }
}