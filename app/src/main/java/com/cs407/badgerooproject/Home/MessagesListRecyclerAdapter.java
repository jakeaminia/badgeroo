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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class MessagesListRecyclerAdapter extends FirestoreRecyclerAdapter<MessagingModel, MessagesListRecyclerAdapter.MessagingModelViewHolder> {

    private MessagesListRecyclerAdapter.Listener listener;
    Context context;
    public MessagesListRecyclerAdapter(@NonNull FirestoreRecyclerOptions<MessagingModel> options, Context context, Listener listener) {
        super(options);
        this.context = context;
        this.listener = listener;
    }

    public interface Listener {
        void onChatClick(String id);

        Context getCurrentContext();
    }

    @Override
    protected void onBindViewHolder(@NonNull MessagingModelViewHolder holder, int position, @NonNull MessagingModel model) {
        //2:38:40
        FirebaseUtil.getOtherUserFromChatroom(model.getUserIds())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        boolean lastMessageSentByMe = model.getLastMessageSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        holder.nameText.setText(task.getResult().get("Name").toString());
                        if(lastMessageSentByMe)
                            holder.lastMessageText.setText("You: " + model.getLastMessage());
                        else
                            holder.lastMessageText.setText(model.getLastMessage());

                        holder.nameText.setText(task.getResult().get("Name").toString());
                        holder.lastMessageText.setText(model.getLastMessage());
                        holder.lastMessageTime.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()));

                        String otherUserId = task.getResult().getId();

                        holder.itemView.setOnClickListener(v -> {
                            //navigate to chat activity
                            listener.onChatClick(otherUserId);

//                            Bundle bundle = new Bundle();
//                            bundle.putString("their_id", otherUserId); // put other user's name in bundle
//                            com.cs407.badgerooproject.Home.MessageFragment messageFragment = new com.cs407.badgerooproject.Home.MessageFragment();
//                            messageFragment.setArguments(bundle);
//
//                            // switch to message fragment
//                            FragmentManager fragmentManager = this.getFragmentManager();
//                            fragmentManager.beginTransaction().replace(R.id.HomeFragmentContainer, messageFragment).commit();
                        });
                    }
                });
    }

    @NonNull
    @Override
    public MessagingModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.msg_list_row, parent, false);
        return new MessagingModelViewHolder(view, listener);
    }



    class MessagingModelViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView lastMessageText;
        TextView lastMessageTime;
        Listener listener;

        public MessagingModelViewHolder(@NonNull View itemView, Listener listener) {
            super(itemView);

            this.listener = listener;
            nameText = itemView.findViewById(R.id.name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
        }

    }
}


