package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cs407.badgerooproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class NameAndGender extends AppCompatActivity {

    EditText name_edt;
    RadioGroup gender_rdg;
    ImageButton arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_and_gender);

        String email = getIntent().getStringExtra("email");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> userProfile = new HashMap<>();


        name_edt = findViewById(R.id.fullName_edt);
        gender_rdg = findViewById(R.id.gender_rdg_fullName);

        arrow = findViewById(R.id.arrow_btn_fullName);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_edt.getText().toString();
                int selectedId = gender_rdg.getCheckedRadioButtonId();
                if (name.isEmpty()){
                    Toast.makeText(NameAndGender.this, "Please enter your name and gender", Toast.LENGTH_LONG).show();
                }else if (selectedId == -1){
                    Toast.makeText(NameAndGender.this, "error 2", Toast.LENGTH_LONG).show();
                }

                else{
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String gender = selectedRadioButton.getText().toString();
                    userProfile.put("Name", name);
                    userProfile.put("Gender", gender);

                    firestore.collection("users").document(userID).set(userProfile, SetOptions.merge())
                            .addOnSuccessListener(aVoid -> {
                                Intent intent = new Intent(NameAndGender.this, UploadProfilePicture.class);
                                Toast.makeText(NameAndGender.this, "Added Successfully", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }).addOnFailureListener(e -> {
                                Toast.makeText(NameAndGender.this, "Failed to add", Toast.LENGTH_LONG).show();
                            });
                }
            }
        });


    }
}