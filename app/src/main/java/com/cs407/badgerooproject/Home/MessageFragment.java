package com.cs407.badgerooproject.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs407.badgerooproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class MessageFragment extends Fragment {

    EditText messageInput;
    ImageButton sendMessageBtn;
    TextView otherName;
    RecyclerView recyclerView;

    String chatroomId;
    MessagingModel messagingModel;
    String sender;
    String recipient;
    MessagesRecyclerAdapter adapter;
    ImageView profilePicture;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        profilePicture = (ImageView) view.findViewById(R.id.message_profile_image);
        messageInput = (EditText) view.findViewById(R.id.message_input);
        sendMessageBtn = (ImageButton) view.findViewById(R.id.message_send_btn);
        otherName = (TextView) view.findViewById(R.id.other_name);
        recyclerView = (RecyclerView) view.findViewById(R.id.message_recycler_view);

        //change text of otherName to name of the other person
        recipient = getArguments().getString("their_id");
        FirebaseFirestore.getInstance().collection("users").document(recipient)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        otherName.setText(documentSnapshot.getString("Name"));

                        String imageUrl = documentSnapshot.getString("imageUrl");
                        String gender = documentSnapshot.getString("Gender");

                        if (imageUrl == null || !imageUrl.startsWith("https://firebasestorage.googleapis.com")) {
                            profilePicture.setImageResource((gender != null && gender.equals("Male"))
                                    ? R.drawable.badger_image_1 : R.drawable.badger_image_2);
                        } else {
                            Glide.with(view).load(imageUrl).into(profilePicture);
                        }
                    }
                });

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getOrCreateChatroomModel();
        setupChatRecyclerView();
        sendMessageBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if (message.isEmpty())
                return;
            sendMessageToUser(message);
        }));
    }

    void setupChatRecyclerView() {
        //2:22:14
        Query query = FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId).collection("chats")
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MessageModel> options = new FirestoreRecyclerOptions.Builder<MessageModel>()
                .setQuery(query, MessageModel.class).build();

        adapter = new MessagesRecyclerAdapter(options, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    void sendMessageToUser(String message) {

        messagingModel.setLastMessageTimestamp(Timestamp.now());
        messagingModel.setLastMessageSenderId(sender);
        messagingModel.setLastMessage(message);
        FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId).set(messagingModel);

        MessageModel messageModel = new MessageModel(message, sender, Timestamp.now());
        //getChatroomMessageReference
        FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId).collection("chats").add(messageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            messageInput.setText("");
                        }
                    }
                });
    }
    void getOrCreateChatroomModel() {
        sender = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recipient = getArguments().getString("their_id"); //other user's id

        if(sender.hashCode() < recipient.hashCode()) {
            chatroomId = sender+"_"+recipient;
        } else {
            chatroomId  = recipient+"_"+sender;
        }

        FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                messagingModel = task.getResult().toObject(MessagingModel.class);
                if(messagingModel == null) {
                    //first time chat
                    messagingModel = new MessagingModel(
                            chatroomId,
                            Arrays.asList(sender, recipient),
                            Timestamp.now(),
                            ""
                    );
                    FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId).set(messagingModel);
                }
            }
        });
    }
}
