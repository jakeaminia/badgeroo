package com.cs407.badgerooproject.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs407.badgerooproject.R;

import java.util.ArrayList;

// Activity: FindRoommatesActivity


public class FindRoommatesFragment extends Fragment {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> pictureURLs = new ArrayList<>();
    private ArrayList<String> messageButtons = new ArrayList<>();
    private ArrayList<String> bios = new ArrayList<>();

    View currentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        names.add("Bob Johnson");
        names.add("Jeff Clark");
        names.add("Henry Roberts");
        bios.add("Likes chocolate");
        bios.add("Super tall");
        bios.add("Real af");
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = currentView.findViewById(R.id.roommatesRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names, pictureURLs, messageButtons, bios);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_find_roommates, container, false);
        initRecyclerView();
        return currentView;
    }
}