package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cs407.badgerooproject.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListViewAdapterSettings extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> listParents;
    private HashMap<String, ArrayList<String>> listChildren;

    public ExpandableListViewAdapterSettings(Context context, ArrayList<String> listParents, HashMap<String, ArrayList<String>> listChildren) {
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
        return this.listChildren.get(this.listParents.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listParents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChildren.get(this.listParents.get(groupPosition)).get(childPosition);
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child_button, null);
        }

        Button listChild = convertView.findViewById(R.id.button2);
        listChild.setText(childTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}