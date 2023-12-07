package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.database.Cursor;
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
import com.cs407.badgerooproject.Setup.DBHelper;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

// Activity: FindRoommatesActivity


public class FindRoommatesFragment extends Fragment implements RecyclerViewAdapter.MessageListener {

    private ArrayList<Roommate> roommates;

    View currentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = currentView.findViewById(R.id.roommatesRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        Log.i("SIZE BEFORE", String.valueOf(roommates.size()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(roommates, (RecyclerViewAdapter.MessageListener) this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_find_roommates, container, false);

//        DBHelper dbHelper = new DBHelper(currentView.getContext());
//        roommates = dbHelper.fetchUsers();
        roommates = new ArrayList<>();
        FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();

        firestoreDatabase.collection("users").get().addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //TODO: don't show the user himself as a potential roommate
                    roommates.add(new Roommate((HashMap<String, Object>) document.getData()));
                }
            }
            initRecyclerView();
            Log.i("SIZE AFTER", String.valueOf(roommates.size()));
        });
        return currentView;
    }


    @Override
    public void onButtonClick(String email) {
        //TODO: implement functionality to start a conversation with this specific user
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.HomeFragmentContainer, new MessageFragment()).commit();
    }
}