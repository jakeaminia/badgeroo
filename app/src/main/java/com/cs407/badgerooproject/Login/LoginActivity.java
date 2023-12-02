package com.cs407.badgerooproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs407.badgerooproject.Home.FindRoommatesActivity;
import com.cs407.badgerooproject.R;
import com.cs407.badgerooproject.Setup.RegisterActivity;

// Fragments: ForgotPasswordFragment, ChangePasswordFragment

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button register = findViewById(R.id.goto_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        Button forgetPassword = findViewById(R.id.goto_forgot_password);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToForgotPassword();
            }
        });

        Button home = findViewById(R.id.goto_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });

    }

    public void goToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToForgotPassword() {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void goToHome() {
        Intent intent = new Intent(this, FindRoommatesActivity.class);
        startActivity(intent);
    }

}