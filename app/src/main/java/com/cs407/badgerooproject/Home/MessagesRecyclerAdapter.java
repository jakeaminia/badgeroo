package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.badgerooproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MessagesRecyclerAdapter extends FirestoreRecyclerAdapter<MessageModel, MessagesRecyclerAdapter.MessageModelViewHolder> {

    Context context;
    public MessagesRecyclerAdapter(@NonNull FirestoreRecyclerOptions<MessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageModelViewHolder holder, int position, @NonNull MessageModel model) {
        if(model.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatText.setText(model.getMessage());
        } else {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.leftChatText.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public MessageModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messages_recycler_row, parent, false);
        return new MessageModelViewHolder(view);
    }

    class MessageModelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatText, rightChatText;

        public MessageModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatText = itemView.findViewById(R.id.left_chat_textview);
            rightChatText = itemView.findViewById(R.id.right_chat_textview);

        }

    }
}