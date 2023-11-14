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

public class SettingsFragment extends Fragment {

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

        listParents.add("parent 1");
        listParents.add("parent 2");
        listParents.add("parent 3");
        listParents.add("parent 4");
        listParents.add("parent 5");

        ArrayList<String> children1 = new ArrayList<>();
        children1.add("Child 1");

        ArrayList<String> children2 = new ArrayList<>();
        children2.add("Child 2");

        ArrayList<String> children3 = new ArrayList<>();
        children3.add("Child 3");

        ArrayList<String> children4 = new ArrayList<>();
        children4.add("Child 4");

        ArrayList<String> children5 = new ArrayList<>();
        children5.add("Child 5");

        listChildren.put(listParents.get(0), children1);
        listChildren.put(listParents.get(1), children2);
        listChildren.put(listParents.get(2), children3);
        listChildren.put(listParents.get(3), children4);
        listChildren.put(listParents.get(4), children5);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_settings, container, false);

        expandableListView = currentView.findViewById(R.id.expandableListSettings);

        showList();

        viewAdapter = new ExpandableListViewAdapterSettings(getContext(), listParents, listChildren);
        expandableListView.setAdapter(viewAdapter);

        return currentView;
    }
}