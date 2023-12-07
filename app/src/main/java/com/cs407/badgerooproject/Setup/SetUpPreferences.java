package com.cs407.badgerooproject.Setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cs407.badgerooproject.Home.FindRoommatesActivity;
import com.cs407.badgerooproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SetUpPreferences extends AppCompatActivity {
    ImageButton arrow_btn;
    EditText minBudget_edt;
    EditText maxBudget_edt;
    EditText roommateNum_edt;
    RadioGroup gender_rdg;
    RadioButton male_rd;
    RadioButton female_rd;
    RadioButton bothGender_rd;
    EditText location_edt;
    RadioGroup housing_style_rdg;
    RadioButton apartment_rd;
    RadioButton house_rd;
    RadioButton bothHousing_rd;
    EditText startDate_edt;
    EditText endDate_edt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_preferences);
        String email = getIntent().getStringExtra("email");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> userProfile = new HashMap<>();

        minBudget_edt = findViewById(R.id.budget_start_edt);
        maxBudget_edt = findViewById(R.id.budget_end_edt);
        roommateNum_edt = findViewById(R.id.num_of_roommates_edt);
        gender_rdg = findViewById(R.id.gender_rdg_preferences);
        male_rd = findViewById(R.id.male_rd);
        female_rd = findViewById(R.id.female_rd);
        bothGender_rd = findViewById(R.id.genderBoth_rd);
        housing_style_rdg = findViewById(R.id.housing_rdg);
        apartment_rd = findViewById(R.id.apartment_rd);
        house_rd = findViewById(R.id.house_rd);
        bothHousing_rd = findViewById(R.id.housingBoth_rd);
        startDate_edt = findViewById(R.id.date_start_edit);
        endDate_edt = findViewById(R.id.date_end_edt);

        arrow_btn = findViewById(R.id.arrow_byn_preference);
        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the input values
                String minBudget = minBudget_edt.getText().toString();
                String maxBudget = maxBudget_edt.getText().toString();
                String roommateNum = roommateNum_edt.getText().toString();
                String startDate = startDate_edt.getText().toString();
                String endDate = endDate_edt.getText().toString();

                // Checking for empty fields
                if(minBudget.isEmpty() || maxBudget.isEmpty() || roommateNum.isEmpty() ||
                        startDate.isEmpty() || endDate.isEmpty() ||
                        gender_rdg.getCheckedRadioButtonId() == -1 ||
                        housing_style_rdg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SetUpPreferences.this, "Oops! Something is left blank.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validating budget inputs
                if(!minBudget.matches("\\d+") || !maxBudget.matches("\\d+")) {
                    Toast.makeText(SetUpPreferences.this, "Budget should be an integer", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validating date formats
                if(!startDate.matches("\\d{2}/\\d{4}") || !endDate.matches("\\d{2}/\\d{4}")) {
                    Toast.makeText(SetUpPreferences.this, "Date should be in MM/YYYY format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Getting values for radio buttons
                int checkedIdGender = gender_rdg.getCheckedRadioButtonId();
                String genderPreference = "";

                if (checkedIdGender == R.id.male_rd) {
                    genderPreference = "Male";
                } else if (checkedIdGender == R.id.female_rd) {
                    genderPreference = "Female";
                } else if (checkedIdGender == R.id.genderBoth_rd) {
                    genderPreference = "Both Male and Female";
                }


                String housingStyle = "";
                int checkedId_housing = housing_style_rdg.getCheckedRadioButtonId();
                if(checkedId_housing == R.id.apartment_rd){
                    housingStyle = "Apartment";
                }else if (checkedId_housing == R.id.house_rd) {
                    housingStyle = "House";
                }else if (checkedId_housing == R.id.housingBoth_rd) {
                    housingStyle = "Both Apartment and House";
                }


                // Update preferences in database
                userProfile.put("minBudget", minBudget);
                userProfile.put("maxBudget", maxBudget);
                userProfile.put("roommateNum", roommateNum);
                userProfile.put("genderPreference", genderPreference);
                userProfile.put("housingStyle", housingStyle);
                userProfile.put("startDate", startDate);
                userProfile.put("endDate", endDate);

                firestore.collection("users").document(userID).set(userProfile, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(SetUpPreferences.this, FindRoommatesActivity.class);
                            Toast.makeText(SetUpPreferences.this, "Added Successfully", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }).addOnFailureListener(e -> {
                            Toast.makeText(SetUpPreferences.this, "Failed to add", Toast.LENGTH_LONG).show();
                        });
            }
        });

    }
}