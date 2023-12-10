package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cs407.badgerooproject.Login.ChangePassword;
import com.cs407.badgerooproject.Login.LoginActivity;
import com.cs407.badgerooproject.R;
import com.cs407.badgerooproject.Setup.SetUpPreferences;
import com.cs407.badgerooproject.Setup.UploadProfilePicture;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> listParents;
    private HashMap<String, String> listChildren;


    public ExpandableListViewAdapter(Context context, ArrayList<String> listParents, HashMap<String, String> listChildren) {
        this.context = context;
        this.listParents = listParents;
        this.listChildren = listChildren;
    }

    @Override
    public int getGroupCount() {
        return this.listParents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listParents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChildren.get(this.listParents.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String parentTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent, null);
        }

        TextView listParent = convertView.findViewById(R.id.listParent);
        listParent.setText(parentTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) getChild(groupPosition, childPosition);
        ;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child_button, null);
        }

        Button listChild = convertView.findViewById(R.id.button2);
        listChild.setText(childTitle);
        switch (childTitle) {
            case "Change Password":
                listChild.setOnClickListener((view) -> context.startActivity(new Intent(context, ChangePassword.class)));
                break;
            case "Delete Account":
                listChild.setOnClickListener((view) -> {
                    LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.confirmdeletepopup, null);
                    Button yesButton = popupView.findViewById(R.id.confirmDeleteYes);
                    Button noButton = popupView.findViewById(R.id.confirmDeleteNo);

                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int height = ViewGroup.LayoutParams.WRAP_CONTENT;

                    PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                    noButton.setOnClickListener(v -> {
                        popupWindow.dismiss();
                    });

                    yesButton.setOnClickListener(v ->
                            {
                                FirebaseAuth.getInstance().getCurrentUser().delete();
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                    );

                    popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
                });



                break;
            case "Edit Preferences":
                listChild.setOnClickListener((view) -> context.startActivity(new Intent(context, SetUpPreferences.class)));
                break;
            case "Edit Bio":
            case "Edit Profile Picture":
                listChild.setOnClickListener((view) -> context.startActivity(new Intent(context, UploadProfilePicture.class)));
                break;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}