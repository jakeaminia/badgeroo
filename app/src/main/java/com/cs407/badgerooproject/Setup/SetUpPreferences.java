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
import com.google.firebase.firestore.DocumentSnapshot;
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
        location_edt = findViewById(R.id.location_edt);
        housing_style_rdg = findViewById(R.id.housing_rdg);
        apartment_rd = findViewById(R.id.apartment_rd);
        house_rd = findViewById(R.id.house_rd);
        bothHousing_rd = findViewById(R.id.housingBoth_rd);
        startDate_edt = findViewById(R.id.date_start_edit);
        endDate_edt = findViewById(R.id.date_end_edt);

        arrow_btn = findViewById(R.id.arrow_byn_preference);

        displayUserPreferences();

        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the input values
                String minBudget = minBudget_edt.getText().toString();
                String maxBudget = maxBudget_edt.getText().toString();
                String roommateNum = roommateNum_edt.getText().toString();
                String startDate = startDate_edt.getText().toString();
                String endDate = endDate_edt.getText().toString();
                String location = location_edt.getText().toString();

                // Checking for empty fields
                if(minBudget.isEmpty() || maxBudget.isEmpty() || roommateNum.isEmpty() ||
                        startDate.isEmpty() || endDate.isEmpty() ||
                        gender_rdg.getCheckedRadioButtonId() == -1 ||
                        housing_style_rdg.getCheckedRadioButtonId() == -1 || location.isEmpty()) {
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
                userProfile.put("location", location);

                firestore.collection("users").document(userID).set(userProfile, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(SetUpPreferences.this, FindRoommatesActivity.class);
                            Toast.makeText(SetUpPreferences.this, "Added/Updated Successfully", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }).addOnFailureListener(e -> {
                            Toast.makeText(SetUpPreferences.this, "Failed to add", Toast.LENGTH_LONG).show();
                        });
            }
        });

    }

    private void displayUserPreferences() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection("users").document(userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Populate the UI with existing data
                    populateUIWithPreferences(document);
                }
            } else {
                // Handle errors or situations where the user does not have preferences set
                Toast.makeText(SetUpPreferences.this, "No existing preferences found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUIWithPreferences(DocumentSnapshot document) {
        minBudget_edt.setText(document.getString("minBudget"));
        maxBudget_edt.setText(document.getString("maxBudget"));
        roommateNum_edt.setText(document.getString("roommateNum"));
        startDate_edt.setText(document.getString("startDate"));
        endDate_edt.setText(document.getString("endDate"));

        // Handle the new 'location' field
        if (document.contains("location")) {
            location_edt.setText(document.getString("location"));
        } else {
            location_edt.setText(""); // or set a default value
        }

        // Set the gender radio group
        String genderPreference = document.getString("genderPreference");
        if ("Male".equals(genderPreference)) {
            male_rd.setChecked(true);
        } else if ("Female".equals(genderPreference)) {
            female_rd.setChecked(true);
        } else if ("Both Male and Female".equals(genderPreference)) {
            bothGender_rd.setChecked(true);
        }

        // Set the housing style radio group
        String housingStyle = document.getString("housingStyle");
        if ("Apartment".equals(housingStyle)) {
            apartment_rd.setChecked(true);
        } else if ("House".equals(housingStyle)) {
            house_rd.setChecked(true);
        } else if ("Both Apartment and House".equals(housingStyle)) {
            bothHousing_rd.setChecked(true);
        }
    }
}