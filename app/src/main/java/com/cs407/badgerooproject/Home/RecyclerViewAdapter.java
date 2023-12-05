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

//    private static final String TAG = "RecyclerViewAdapter";

    private MessageListener mMessageListener;
//    private ArrayList<String> names;
//    private ArrayList<String> pictureURLs;
//    private ArrayList<String> messageButtons;
//    private ArrayList<String> bios;

    private ArrayList<Roommate> roommates;

    public RecyclerViewAdapter(ArrayList<Roommate> roommates,
            /*ArrayList<String> names, ArrayList<String> pictureURLs, ArrayList<String> messageButtons, ArrayList<String> bios,*/
                               MessageListener messageListener) {
        this.mMessageListener = messageListener;
//        this.names = names;
//        this.pictureURLs = pictureURLs;
//        this.messageButtons = messageButtons;
//        this.bios = bios;
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
//        holder.name.setText(names.get(position));
        holder.name.setText(currentRoommate.getFullName());

//        holder.bio.setText(bios.get(position));
        holder.bio.setText(currentRoommate.toString());

//        holder.messageButton.setText(String.format("Message %s", names.get(position).split(" ")[0]));
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