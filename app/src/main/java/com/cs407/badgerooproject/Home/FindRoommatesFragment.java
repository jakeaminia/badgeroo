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

import java.util.ArrayList;

// Activity: FindRoommatesActivity


public class FindRoommatesFragment extends Fragment implements RecyclerViewAdapter.MessageListener {

    private ArrayList<Roommate> roommates;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> pictureURLs = new ArrayList<>();
    private ArrayList<String> messageButtons = new ArrayList<>();
    private ArrayList<String> bios = new ArrayList<>();

    View currentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = currentView.findViewById(R.id.roommatesRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        for (Roommate roommate: roommates) {
            names.add(roommate.getFullName());
            pictureURLs.add(roommate.getProfilePicture());
            bios.add(roommate.getBio());
        }

        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(roommates, (RecyclerViewAdapter.MessageListener) this);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names, pictureURLs, messageButtons, bios, (RecyclerViewAdapter.MessageListener) this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_find_roommates, container, false);
        DBHelper dbHelper = new DBHelper(currentView.getContext());

        roommates = dbHelper.fetchUsers();

        initRecyclerView();
        return currentView;
    }


    @Override
    public void onButtonClick(int position) {
        //TODO: implement functionality to start a conversation with this specific user
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.HomeFragmentContainer, new MessageFragment()).commit();
    }
}