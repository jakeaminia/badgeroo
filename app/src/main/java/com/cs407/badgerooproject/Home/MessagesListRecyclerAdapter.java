package com.cs407.badgerooproject.Home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cs407.badgerooproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

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

                        DocumentSnapshot result = task.getResult();

                        String otherUserName = result.getString("Name");
                        holder.nameText.setText(otherUserName);

                        String imageUrl = result.contains("imageUrl") ? result.getString("imageUrl") : null;
                        if (imageUrl == null || !imageUrl.startsWith("https://firebasestorage.googleapis.com")) {
                            holder.profileImage.setImageResource(R.drawable.bucky);
                        } else {
                            Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.profileImage);
                        }

                        boolean lastMessageSentByMe = model.getLastMessageSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        String displayText;
                        SpannableString spannableString;

                        if (lastMessageSentByMe) {
                            displayText = "You: " + model.getLastMessage();
                            spannableString = new SpannableString(displayText);
                            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            displayText = otherUserName + ": " + model.getLastMessage();
                            spannableString = new SpannableString(displayText);
                            int boldEndIndex = otherUserName.length();
                            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, boldEndIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }

                        holder.lastMessageText.setText(spannableString);
                        holder.lastMessageTime.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()));

                        String otherUserId = task.getResult().getId();

                        holder.itemView.setOnClickListener(v -> {
                            listener.onChatClick(otherUserId);
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
        CircleImageView profileImage;

        public MessagingModelViewHolder(@NonNull View itemView, Listener listener) {
            super(itemView);

            this.listener = listener;
            nameText = itemView.findViewById(R.id.name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profileImage = itemView.findViewById(R.id.profile_image);
        }

    }
}