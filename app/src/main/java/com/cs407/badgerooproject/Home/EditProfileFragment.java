package com.cs407.badgerooproject.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cs407.badgerooproject.R;

import java.util.ArrayList;
import java.util.HashMap;

// Activity: FindRoommatesActivity

public class EditProfileFragment extends Fragment {

    View currentView;
    ExpandableListViewAdapterSettings viewAdapter;
    ExpandableListView expandableListView;
    ArrayList<String> listParents;
    HashMap<String, ArrayList<String>> listChildren;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("message", String.valueOf(getActivity()));
    }

    private void showList() {
        listParents = new ArrayList<>();
        listChildren = new HashMap<>();

        listParents.add("Edit Preferences");
        listParents.add("Edit Bio");
        listParents.add("Edit Profile Picture");

        ArrayList<String> children1 = new ArrayList<>();
        children1.add("Edit Preferences");

        ArrayList<String> children2 = new ArrayList<>();
        children2.add("Edit Bio");

        ArrayList<String> children3 = new ArrayList<>();
        children3.add("Edit Profile Picture");


        listChildren.put(listParents.get(0), children1);
        listChildren.put(listParents.get(1), children2);
        listChildren.put(listParents.get(2), children3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        expandableListView = currentView.findViewById(R.id.expandableListEditProfile);

        showList();

        viewAdapter = new ExpandableListViewAdapterSettings(getContext(), listParents, listChildren);
        expandableListView.setAdapter(viewAdapter);

        return currentView;
    }
}