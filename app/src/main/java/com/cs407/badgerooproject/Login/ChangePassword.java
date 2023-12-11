package com.cs407.badgerooproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs407.badgerooproject.Home.FindRoommatesActivity;
import com.cs407.badgerooproject.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText currentPasswordEditText, newPasswordEditText, confirmNewPasswordEditText;
    Button changePasswordButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        auth = FirebaseAuth.getInstance();

        currentPasswordEditText = findViewById(R.id.current_password);
        currentPasswordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (currentPasswordEditText.getRight() - currentPasswordEditText.getCompoundPaddingRight())) {
                    // Toggle password visibility
                    int inputType = currentPasswordEditText.getInputType();
                    boolean isPasswordVisible = (inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                    currentPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | (isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD));
                    currentPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_password_icon, 0, isPasswordVisible ? R.drawable.password_hide_icon : R.drawable.password_see_icon, 0);

                    // Move cursor to the end of text
                    currentPasswordEditText.setSelection(currentPasswordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });

        newPasswordEditText = findViewById(R.id.change_password);
        newPasswordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (newPasswordEditText.getRight() - newPasswordEditText.getCompoundPaddingRight())) {
                    // Toggle password visibility
                    int inputType = newPasswordEditText.getInputType();
                    boolean isPasswordVisible = (inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                    newPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | (isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD));
                    newPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_password_icon, 0, isPasswordVisible ? R.drawable.password_hide_icon : R.drawable.password_see_icon, 0);

                    // Move cursor to the end of text
                    newPasswordEditText.setSelection(newPasswordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });

        confirmNewPasswordEditText = findViewById(R.id.confirm_password);
        confirmNewPasswordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (confirmNewPasswordEditText.getRight() - confirmNewPasswordEditText.getCompoundPaddingRight())) {
                    // Toggle password visibility
                    int inputType = confirmNewPasswordEditText.getInputType();
                    boolean isPasswordVisible = (inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                    confirmNewPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | (isPasswordVisible ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD));
                    confirmNewPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.login_password_icon, 0, isPasswordVisible ? R.drawable.password_hide_icon : R.drawable.password_see_icon, 0);

                    // Move cursor to the end of text
                    confirmNewPasswordEditText.setSelection(confirmNewPasswordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });

        changePasswordButton = findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(v -> {

            String currentPassword = currentPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

            if (!newPassword.equals(confirmNewPassword)) {
                Toast.makeText(ChangePassword.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {

                String email = user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

                user.reauthenticate(credential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Toast.makeText(ChangePassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ChangePassword.this, FindRoommatesActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(ChangePassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(ChangePassword.this, "Current Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

}