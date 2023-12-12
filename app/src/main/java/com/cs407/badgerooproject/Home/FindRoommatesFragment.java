package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs407.badgerooproject.Login.LoginActivity;
import com.cs407.badgerooproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FindRoommatesFragment extends Fragment implements RecyclerViewAdapter.MessageListener {

    private ArrayList<Roommate> roommates;
    private FirebaseFirestore firestoreInstance = FirebaseFirestore.getInstance();
    private CollectionReference users = firestoreInstance.collection("users");
    private View currentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roommates = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_find_roommates, container, false);
        return currentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        try {
            String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            HashMap<String, Object> currentUserData = new HashMap<>();

            users.document(currentUserID).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    currentUserData.putAll(documentSnapshot.getData());
                }

                users.get().addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        roommates.clear(); // Clear the existing data
                        boolean idsNotEqual, roommateGenderPreferenceSatisfied, userGenderPreferenceSatisfied, housingPreferenceSatisfied, datesAlign, pricesAlign;
                        String roommateGenderPreference, userGenderPreference, roommateGender, userGender, roommateHousingStyle, userHousingStyle;
                        String[] userMinDate, userMaxDate, roommateMinDate, roommateMaxDate;
                        int userMinPrice, userMaxPrice, roommateMinPrice, roommateMaxPrice, userMinDateInt, userMaxDateInt, roommateMinDateInt, roommateMaxDateInt;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> documentData = (HashMap<String, Object>) document.getData();
                            // Your existing conditions...
                            roommateGenderPreference = (String) documentData.get("genderPreference");
                            userGenderPreference = (String) currentUserData.get("genderPreference");
                            roommateGender = (String) documentData.get("Gender");
                            userGender = (String) currentUserData.get("Gender");
                            roommateHousingStyle = (String) documentData.get("housingStyle");
                            userHousingStyle = (String) currentUserData.get("housingStyle");

                            try {
                                userMinPrice = Integer.parseInt((String) currentUserData.get("minBudget"));
                                userMaxPrice = Integer.parseInt((String) currentUserData.get("maxBudget"));
                                roommateMinPrice = Integer.parseInt((String) documentData.get("minBudget"));
                                roommateMaxPrice = Integer.parseInt((String) documentData.get("maxBudget"));

                                pricesAlign = userMinPrice <= roommateMaxPrice && roommateMinPrice <= userMaxPrice;

                            } catch (NumberFormatException e) {
                                pricesAlign = true;
                            }


                            try {
                                userMinDate = ((String) currentUserData.get("startDate")).split("/");
                                userMaxDate = ((String) currentUserData.get("endDate")).split("/");
                                roommateMinDate = ((String) documentData.get("startDate")).split("/");
                                roommateMaxDate = ((String) documentData.get("endDate")).split("/");

                                userMinDateInt = Integer.parseInt(userMinDate[1] + userMinDate[0]);
                                userMaxDateInt = Integer.parseInt(userMaxDate[1] + userMaxDate[0]);
                                roommateMinDateInt = Integer.parseInt(roommateMinDate[1] + roommateMinDate[0]);
                                roommateMaxDateInt = Integer.parseInt(roommateMaxDate[1] + roommateMaxDate[0]);

                                datesAlign = userMinDateInt <= roommateMaxDateInt && roommateMinDateInt <= userMaxDateInt;
                            } catch (NumberFormatException | NullPointerException e) {
                                datesAlign = true;
                            }

                            idsNotEqual = !Objects.equals(documentData.get("id"), currentUserID);
                            roommateGenderPreferenceSatisfied = Objects.equals(roommateGenderPreference, "Both Male and Female") || Objects.equals(roommateGenderPreference, userGender);
                            userGenderPreferenceSatisfied = Objects.equals(userGenderPreference, "Both Male and Female") || Objects.equals(userGenderPreference, roommateGender);
                            housingPreferenceSatisfied = Objects.equals(userHousingStyle, roommateHousingStyle) || Objects.equals(userHousingStyle, "Both Apartment and House") || Objects.equals(roommateHousingStyle, "Both Apartment and House");

                            String booleanLog = String.format("ids: %s, roommateGenderPref: %s, userGenderPref: %s, housing: %s, dates: %s, prices: %s\n", idsNotEqual, roommateGenderPreferenceSatisfied, userGenderPreferenceSatisfied, housingPreferenceSatisfied, datesAlign, pricesAlign);
                            Log.i("BOOLEAN LOG", booleanLog);

                            if (idsNotEqual && roommateGenderPreferenceSatisfied && userGenderPreferenceSatisfied && housingPreferenceSatisfied && datesAlign && pricesAlign) {
                                documentData.put("id", document.getId());
                                roommates.add(new Roommate(documentData));
                            }
                        }
                        initRecyclerView(); // Initialize RecyclerView with the new data
                    }
                });
            });
        } catch (NullPointerException e) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = currentView.findViewById(R.id.roommatesRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(roommates, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onButtonClick(String id) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString("their_id", id);
        args.putString("my_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.HomeFragmentContainer, fragment).commit();
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }
}