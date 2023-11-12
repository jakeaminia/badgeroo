package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.badgerooproject.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> pictureURLs = new ArrayList<>();
    private ArrayList<String> messageButtons = new ArrayList<>();
    private ArrayList<String> bios = new ArrayList<>();

    public RecyclerViewAdapter(ArrayList<String> names, ArrayList<String> pictureURLs, ArrayList<String> messageButtons, ArrayList<String> bios) {
        this.names = names;
        this.pictureURLs = pictureURLs;
        this.messageButtons = messageButtons;
        this.bios = bios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roommate_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(names.get(position));
        holder.bio.setText(bios.get(position));
        holder.messageButton.setText(String.format("Message %s", names.get(position).split(" ")[0]));
        holder.messageButton.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        Button messageButton;
        TextView name;
        TextView bio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture = itemView.findViewById(R.id.roommate_picture);
            messageButton = itemView.findViewById(R.id.message_roommate);
            name = itemView.findViewById(R.id.roommate_name);
            bio = itemView.findViewById(R.id.roommate_bio);
        }
    }
}
