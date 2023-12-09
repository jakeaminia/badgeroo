package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs407.badgerooproject.R;

import com.google.firebase.auth.FirebaseAuth;
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

//        DBHelper dbHelper = new DBHelper(currentView.getContext());
//        roommates = dbHelper.fetchUsers();
        roommates = new ArrayList<>();
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, Object> currentUserData = new HashMap<>();
        firestoreDatabase.collection("users").get().addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getId().equals(currentUserID)) {
                        currentUserData.putAll(document.getData());
                        break;
                    }
                }
                for (QueryDocumentSnapshot document : task.getResult()) {
                    HashMap<String, Object> documentData = (HashMap<String, Object>) document.getData();
                    if (!document.getId().equals(currentUserID)
                            && (Objects.equals(documentData.get("Gender"), currentUserData.get("genderPreference")) || Objects.equals(currentUserData.get("genderPreference"), "Both Male and Female"))
                            && (Objects.equals(currentUserData.get("Gender"), documentData.get("genderPreference")) || Objects.equals(documentData.get("genderPreference"), "Both Male and Female"))
                            && (Objects.equals(documentData.get("housingStyle"), "Both Apartment and House") || Objects.equals(currentUserData.get("housingStyle"), "Both Apartment and House") || Objects.equals(documentData.get("housingStyle"), currentUserData.get("housingStyle")))) {
                        roommates.add(new Roommate(documentData));
                    }
                }
            }
            initRecyclerView();
        });
        return currentView;
    }


    @Override
    public void onButtonClick(String email) {
        //TODO: implement functionality to start a conversation with this specific user
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.HomeFragmentContainer, new MessageFragment()).commit();
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }
}