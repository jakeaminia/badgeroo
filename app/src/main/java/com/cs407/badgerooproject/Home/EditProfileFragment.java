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

import com.cs407.badgerooproject.R;
import com.cs407.badgerooproject.Setup.SetUpPreferences;
import com.cs407.badgerooproject.Setup.UploadProfilePicture;

import java.util.ArrayList;
import java.util.HashMap;

// Activity: FindRoommatesActivity

public class EditProfileFragment extends Fragment implements ExpandableListViewAdapter.ButtonListener {

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

        listParents.add("Edit Preferences");
        listParents.add("Edit Bio");
        listParents.add("Edit Profile Picture");

        listChildren.put(listParents.get(0), "Edit Preferences");
        listChildren.put(listParents.get(1), "Edit Bio");
        listChildren.put(listParents.get(2), "Edit Profile Picture");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        expandableListView = currentView.findViewById(R.id.expandableListEditProfile);

        showList();

        viewAdapter = new ExpandableListViewAdapter(getContext(), listParents, listChildren, this);
        expandableListView.setAdapter(viewAdapter);

        return currentView;
    }

    @Override
    public void onButtonClick(String buttonName) {
        Intent intent;
        switch (buttonName) {
            case "Edit Preferences":
                intent = new Intent(getContext(), SetUpPreferences.class);
                startActivity(intent);
                break;
            case "Edit Bio":
            case "Edit Profile Picture":
                intent = new Intent(getContext(), UploadProfilePicture.class);
                startActivity(intent);
                break;
        }
    }
}