package com.cs407.badgerooproject.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cs407.badgerooproject.Login.ForgotPassword;
import com.cs407.badgerooproject.Login.LoginActivity;
import com.cs407.badgerooproject.R;

import java.util.ArrayList;
import java.util.HashMap;

// Activity: FindRoommatesActivity

public class SettingsFragment extends Fragment {

    View currentView;
    ExpandableListViewAdapter viewAdapter;
    ExpandableListView expandableListView;
    ArrayList<String> listParents;
    HashMap<String, String> listChildren;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("message", String.valueOf(getActivity()));
    }

    private void showList() {
        listParents = new ArrayList<>();
        listChildren = new HashMap<>();

        listParents.add("Change Password");
        listParents.add("Delete Account");

        listChildren.put(listParents.get(0), "Change Password");
        listChildren.put(listParents.get(1), "Delete Account");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_settings, container, false);

        expandableListView = currentView.findViewById(R.id.expandableListSettings);

        showList();

        viewAdapter = new ExpandableListViewAdapter(getContext(), listParents, listChildren);
        expandableListView.setAdapter(viewAdapter);

        return currentView;
    }
}