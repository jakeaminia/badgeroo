package com.cs407.badgerooproject.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs407.badgerooproject.Home.FindRoommatesActivity;
import com.cs407.badgerooproject.R;
import com.cs407.badgerooproject.Setup.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button forgotPassword_btn;
    Button register;
    Button login_btn;
    EditText email_edt;
    EditText password_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // User is already signed in, navigate to the home activity
            goToHome();
            return;
        }

        auth = FirebaseAuth.getInstance();

        email_edt = findViewById(R.id.login_email);
        password_edt = findViewById(R.id.login_password);
        password_edt.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (password_edt.getRight() - password_edt.getCompoundPaddingRight())) {
                    // Toggle password visibility
                    int inputType = password_edt.getInputType();
                    boolean isPasswordVisible = (inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                    password_edt.setInputType(InputType.TYPE_CLASS_TEXT | (isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD));
                    password_edt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_password_icon, 0, isPasswordVisible ? R.drawable.password_hide_icon : R.drawable.password_see_icon, 0);

                    // Move cursor to the end of text
                    password_edt.setSelection(password_edt.getText().length());
                    return true;
                }
            }
            return false;
        });

        register = findViewById(R.id.goto_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        forgotPassword_btn = findViewById(R.id.goto_forgot_password);
        forgotPassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToForgotPassword();
            }
        });

        login_btn = findViewById(R.id.goto_home);
        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String email = email_edt.getText().toString();
                String password = password_edt.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email and password", Toast.LENGTH_LONG).show();
                } else {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = auth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Logging in.",
                                                Toast.LENGTH_SHORT).show();
                                        goToHome();
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

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
        finish();
    }

}