package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs407.badgerooproject.R;

// Fragments: None

public class RegisterActivity extends AppCompatActivity {
    Button reg_btn;
    EditText email_edt;
    EditText password_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.passwrod_edt);
        reg_btn = findViewById(R.id.register_btn);

        //Initialize database
        DBHelper dbHelper = new DBHelper(this);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_edt.getText().toString();
                String password = password_edt.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter your email and password", Toast.LENGTH_LONG).show();
                }else{
                    dbHelper.insertLoginCredentials(email, password);
                    Intent intent = new Intent(RegisterActivity.this, NameAndGender.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });

    }
}