package com.cs407.badgerooproject.Home;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtil {

    public static DocumentReference getOtherUserFromChatroom(List<String> userIds) {
        if(userIds.get(0).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return FirebaseFirestore.getInstance().collection("users").document(userIds.get(1));
        } else {
            return FirebaseFirestore.getInstance().collection("users").document(userIds.get(0));
        }
    }

    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("hh:mm aa - MM/dd/yy").format(timestamp.toDate());
    }

}