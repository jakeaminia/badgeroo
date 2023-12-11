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
import android.widget.ImageView;
import android.widget.TextView;

import com.cs407.badgerooproject.Login.LoginActivity;
import com.cs407.badgerooproject.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

// Activity: FindRoommatesActivity


public class FindRoommatesFragment extends Fragment implements RecyclerViewAdapter.MessageListener {

    private ArrayList<Roommate> roommates;
    FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();


    View currentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = currentView.findViewById(R.id.roommatesRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(roommates, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_find_roommates, container, false);

        roommates = new ArrayList<>();

        try {
            String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            HashMap<String, Object> currentUserData = new HashMap<>();

            String currUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DocumentReference currentUserDoc = FirebaseFirestore.getInstance().collection("users").document(currUserId);
            currentUserDoc.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    currentUserData.putAll(documentSnapshot.getData());
                }
            });

            firestoreDatabase.collection("users").get().addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        HashMap<String, Object> documentData = (HashMap<String, Object>) document.getData();
                        if (!document.getId().equals(currentUserID)
                                && (Objects.equals(documentData.get("Gender"), currentUserData.get("genderPreference")) || Objects.equals(currentUserData.get("genderPreference"), "Both Male and Female"))
                                && (Objects.equals(currentUserData.get("Gender"), documentData.get("genderPreference")) || Objects.equals(documentData.get("genderPreference"), "Both Male and Female"))
                                && (Objects.equals(documentData.get("housingStyle"), "Both Apartment and House") || Objects.equals(currentUserData.get("housingStyle"), "Both Apartment and House") || Objects.equals(documentData.get("housingStyle"), currentUserData.get("housingStyle")))) {
                            documentData.put("id", document.getId());
                            roommates.add(new Roommate(documentData));
                        }
                    }
                }
                initRecyclerView();
            });
        } catch (NullPointerException e) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        return currentView;
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