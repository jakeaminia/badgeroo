package com.cs407.badgerooproject.Home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs407.badgerooproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MessagesListFragment extends Fragment implements MessagesListRecyclerAdapter.Listener {

    RecyclerView recyclerView;

    MessagesListRecyclerAdapter adapter;
    private static Activity myContext;


    public MessagesListFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);
        myContext = (Activity) container.getContext();

        recyclerView = view.findViewById(R.id.msg_list_recycler_view);
        setupRecyclerView();

        return view;

    }

    void setupRecyclerView() {

        //TODO: setupRecyclerView()

        Query query = FirebaseFirestore.getInstance().collection("chatrooms")
                .whereArrayContains("userIds", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MessagingModel> options = new FirestoreRecyclerOptions.Builder<MessagingModel>()
                .setQuery(query, MessagingModel.class).build();

        adapter = new MessagesListRecyclerAdapter(options, getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onChatClick(String id) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString("their_id", id);
        args.putString("my_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.HomeFragmentContainer, fragment).commit();
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }
}
