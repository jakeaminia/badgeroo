package com.cs407.badgerooproject.Home;

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

    private MessageListener mMessageListener;

    private ArrayList<Roommate> roommates;

    public RecyclerViewAdapter(ArrayList<Roommate> roommates, MessageListener messageListener) {
        this.mMessageListener = messageListener;
        this.roommates = roommates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roommate_profile, parent, false);
        return new ViewHolder(view, mMessageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Roommate currentRoommate = roommates.get(position);

        holder.name.setText(currentRoommate.getFullName());

        holder.bio.setText(currentRoommate.toString());

        holder.messageButton.setText(String.format("Message %s", currentRoommate.getFullName().split(" ")[0]));

        holder.messageButton.setOnClickListener(v -> {
            mMessageListener.onButtonClick(currentRoommate.getEmail());
        });
    }

    @Override
    public int getItemCount() {
        return roommates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        Button messageButton;
        TextView name;
        TextView bio;

        MessageListener messageListener;

        public ViewHolder(@NonNull View itemView, MessageListener messageListener) {
            super(itemView);

            this.messageListener = messageListener;
            profilePicture = itemView.findViewById(R.id.roommate_picture);
            messageButton = itemView.findViewById(R.id.message_roommate);
            name = itemView.findViewById(R.id.roommate_name);
            bio = itemView.findViewById(R.id.roommate_bio);
        }

    }

    public interface MessageListener {
        void onButtonClick(String email);
    }
}