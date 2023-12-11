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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs407.badgerooproject.R;
import com.cs407.badgerooproject.Setup.SetUpPreferences;
import com.cs407.badgerooproject.Setup.UploadProfilePicture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

// Activity: FindRoommatesActivity

public class EditProfileFragment extends Fragment {

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

    private void showProfile() {
        ImageView profilePic = currentView.findViewById(R.id.myPfp);
        TextView name = currentView.findViewById(R.id.myName);
        TextView bio = currentView.findViewById(R.id.myBio);

        String currUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference currentUserDoc = FirebaseFirestore.getInstance().collection("users").document(currUserId);

        currentUserDoc.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                HashMap<String, Object> objData = (HashMap<String, Object>) documentSnapshot.getData();

                name.setText((String) objData.get("Name"));
                bio.setText((String) objData.get("bio"));

                String imageUrl = (String) objData.get("imageUrl");
                String gender = (String) objData.get("Gender");

                if (imageUrl == null || !imageUrl.startsWith("https://firebasestorage.googleapis.com")) {
                    profilePic.setImageResource((gender.equals("Male"))
                            ? R.drawable.badger_image_1 : R.drawable.badger_image_2);
                } else {
                    Glide.with(currentView).load(imageUrl).into(profilePic);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        expandableListView = currentView.findViewById(R.id.expandableListEditProfile);

        showList();
        showProfile();

        viewAdapter = new ExpandableListViewAdapter(getContext(), listParents, listChildren);
        expandableListView.setAdapter(viewAdapter);

        return currentView;
    }
}