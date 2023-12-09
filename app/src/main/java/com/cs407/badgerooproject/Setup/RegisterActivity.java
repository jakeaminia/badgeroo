package com.cs407.badgerooproject.Setup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

// Fragments: None

public class RegisterActivity extends AppCompatActivity {
    Button reg_btn;
    EditText email_edt;
    EditText password_edt;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        reg_btn = findViewById(R.id.register_btn);

        //Initialize database
        DBHelper dbHelper = new DBHelper(this);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_edt.getText().toString();
                String password = password_edt.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter your email and password", Toast.LENGTH_LONG).show();
                    return;
                }else{
//                    dbHelper.insertLoginCredentials(email, password);
//                    Intent intent = new Intent(RegisterActivity.this, NameAndGender.class);
//                    intent.putExtra("email", email);
//                    startActivity(intent);

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