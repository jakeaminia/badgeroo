package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> documentData = (HashMap<String, Object>) document.getData();
                            // Your existing conditions...
                            if (!document.getId().equals(currentUserID) /* other conditions */) {
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