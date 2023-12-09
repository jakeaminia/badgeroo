package com.cs407.badgerooproject.Setup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cs407.badgerooproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadProfilePicture extends AppCompatActivity {

    ImageButton arrow_btn;
    ImageButton upload_btn;
    CircleImageView selectedImageView;
    private ActivityResultLauncher<String> mGetContent;
    private Uri photoUri;

    EditText bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_picture);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        upload_btn = findViewById(R.id.upload_btn);
        selectedImageView = findViewById(R.id.circleImageView);
        bio = findViewById(R.id.bio_edt);

        displayProfilePicture();



        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                @Override
                    public void onActivityResult(Uri uri) {
                            // Handle the returned Uri
                        if (uri != null) {
                                selectedImageView.setImageURI(uri);
                                //Everytime user select a different photo, the uri is updated
                                photoUri = uri;
                        }
                    }
                });

        upload_btn.setOnClickListener(v -> mGetContent.launch("image/*"));

        arrow_btn = findViewById(R.id.arrow_btn_picture);

        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userBio = bio.getText().toString();
                if (photoUri != null) {
                    StorageReference imageRef = storageRef.child("images/" + UUID.randomUUID().toString());
                    imageRef.putFile(photoUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                // Image uploaded successfully
                                // Now get the download URL
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    // Call the method to update Firestore
                                    updateUserDocument(imageUrl);
                                });
                            })
                            .addOnFailureListener(e -> {
                                // Handle unsuccessful uploads
                                // ...
                            });
                }else{
                    Toast.makeText(UploadProfilePicture.this, "No photo selected", Toast.LENGTH_SHORT).show();
                }

                if (userBio != null || !userBio.isEmpty()){
                    updateBio(userBio);
                }
                Intent intent = new Intent(UploadProfilePicture.this, SetUpPreferences.class);
                startActivity(intent);

            }
        });
        }

    private void updateUserDocument(String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDocRef = db.collection("users").document(userId);


            userDocRef.update("imageUrl", imageUrl)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(UploadProfilePicture.this,"Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(UploadProfilePicture.this,"Fail to update profile picture", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void updateBio(String bio) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDocRef = db.collection("users").document(userId);
            userDocRef.update("bio", bio).
                    addOnSuccessListener(aVoid -> {
                        Toast.makeText(UploadProfilePicture.this, "Bio updated successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(UploadProfilePicture.this, "Fail to update Bio", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void displayProfilePicture(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){

            String userID = currentUser.getUid();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            DocumentReference userDocument = firestore.collection("users").document(userID);

            userDocument.get().addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()){
                    String imageUrl = documentSnapshot.getString("imageUrl");
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(this)
                                .load(imageUrl)
                                .into(selectedImageView);
                    }

                    String userBio = documentSnapshot.getString("bio");
                    if (userBio != null && !userBio.isEmpty()) {
                        bio.setText(userBio);
                    }else{
                        Toast.makeText(UploadProfilePicture.this, "No bio", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(UploadProfilePicture.this, "Fail to load picture", Toast.LENGTH_SHORT).show();
            });

        }else{
            Toast.makeText(UploadProfilePicture.this, "Current user is null", Toast.LENGTH_SHORT).show();
        }
    }

}
