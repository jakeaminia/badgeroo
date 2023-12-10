package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.badgerooproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MessagesListRecyclerAdapter extends FirestoreRecyclerAdapter<MessagingModel, MessagesListRecyclerAdapter.MessagingModelViewHolder> {

    Context context;
    public MessagesListRecyclerAdapter(@NonNull FirestoreRecyclerOptions<MessagingModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessagingModelViewHolder holder, int position, @NonNull MessagingModel model) {
        //2:38:40
        FirebaseUtil.getOtherUserFromChatroom(model.getUserIds())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        holder.nameText.setText(task.getResult().get("Name").toString());
                        holder.lastMessageText.setText(model.getLastMessage());
                        holder.lastMessageTime.setText(model.getLastMessageTimestamp().toString());
                    }
                });

        holder.itemView.setOnClickListener(v -> {
            //navigate to chat activity
            Bundle bundle = new Bundle();
            bundle.putString("user", holder.nameText.toString()); // put other user's name in bundle
            com.cs407.badgerooproject.Home.MessageFragment messageFragment = new com.cs407.badgerooproject.Home.MessageFragment();
            messageFragment.setArguments(bundle);

            // switch to message fragment
            FragmentManager fragmentManager = messageFragment.getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.HomeFragmentContainer, messageFragment).commit();
        });
    }

    @NonNull
    @Override
    public MessagingModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.msg_list_row, parent, false);
        return new MessagingModelViewHolder(view);
    }

    class MessagingModelViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView lastMessageText;
        TextView lastMessageTime;

        public MessagingModelViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
        }

    }
}


