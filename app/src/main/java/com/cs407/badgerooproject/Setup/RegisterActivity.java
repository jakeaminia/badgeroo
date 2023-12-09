package com.cs407.badgerooproject.Setup;

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

import com.cs407.badgerooproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email_edt;
    EditText password_edt;
    EditText confirm_password_edt;
    Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
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

        confirm_password_edt = findViewById(R.id.confirm_password_edt);
        confirm_password_edt.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (confirm_password_edt.getRight() - confirm_password_edt.getCompoundPaddingRight())) {
                    // Toggle password visibility
                    int inputType = confirm_password_edt.getInputType();
                    boolean isPasswordVisible = (inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                    confirm_password_edt.setInputType(InputType.TYPE_CLASS_TEXT | (isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD));
                    confirm_password_edt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_password_icon, 0, isPasswordVisible ? R.drawable.password_hide_icon : R.drawable.password_see_icon, 0);

                    // Move cursor to the end of text
                    confirm_password_edt.setSelection(confirm_password_edt.getText().length());
                    return true;
                }
            }
            return false;
        });


        reg_btn = findViewById(R.id.register_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_edt.getText().toString();
                String password = password_edt.getText().toString();
                String confirmPassword = confirm_password_edt.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter your email and password", Toast.LENGTH_LONG).show();
                }

                else if (!confirmPassword.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_LONG).show();
                }

                else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(RegisterActivity.this, "Account created.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, NameAndGender.class);
                                        intent.putExtra("email", email);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

    }
}