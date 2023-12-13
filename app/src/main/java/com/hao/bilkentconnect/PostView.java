package com.hao.bilkentconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Comment;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityPostViewBinding;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostView extends AppCompatActivity {

    private ActivityPostViewBinding binding;
    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostViewBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        postId = getIntent().getStringExtra("post_id"); // Retrieve post ID


        commentArrayList = new ArrayList<>();
        loadCommentsFromFirebase();


        binding.recyclerCommentView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentArrayList);
        binding.recyclerCommentView.setAdapter(commentAdapter);
    }

    public void goToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void SendConnectionRequest(View view) {
        /*Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();*/
    }

    public void shareAnonymousComment(View view) {
        String commentText = binding.commentText.getText().toString();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Empty Comment cannot be shared", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();

        // Include postId in the comment
        Comment newComment = new Comment(userId, commentText, true, postId);

        Map<String, Object> commentValues = newComment.toMap();

        firebaseFirestore.collection("Comments").add(commentValues)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(PostView.this, "Comment shared successfully", Toast.LENGTH_LONG).show();
                    binding.commentText.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(PostView.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());


    }

    /*public void shareRegularComment(View view) {
        String commentText = binding.commentText.getText().toString();

        if(commentText.isEmpty()) {
            Toast.makeText(this, "Empty Comment cannot be share", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();


        Comment newComment = new Comment(userId, commentText, false);

        // Convert Comment object to Map if needed, or directly save the Comment object
        Map<String, Object> commentValues = newComment.toMap();

        // Add comment to Firebase
        firebaseFirestore.collection("Comments").add(commentValues).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(PostView.this, "Comment shared successfully", Toast.LENGTH_LONG).show();
                // Reset the comment input field
                binding.commentText.setText("");
                // You might want to refresh comments or perform other actions
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostView.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
    public void shareRegularComment(View view) {
        String commentText = binding.commentText.getText().toString();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Empty Comment cannot be shared", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();

        // Include postId in the comment
        Comment newComment = new Comment(userId, commentText, false, postId);

        Map<String, Object> commentValues = newComment.toMap();

        firebaseFirestore.collection("Comments").add(commentValues)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(PostView.this, "Comment shared successfully", Toast.LENGTH_LONG).show();
                    binding.commentText.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(PostView.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }

    public void loadCommentsFromFirebase() {
        CollectionReference commentsCollection = firebaseFirestore.collection("Comments");

        // Filter comments by the post ID
        commentsCollection.whereEqualTo("postId", postId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            Toast.makeText(PostView.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (queryDocumentSnapshots != null) {

                            commentArrayList.clear(); // Clear existing comments

                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                                HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getData();
                                String commentSenderId = (String) data.get("userId");
                                String commentId = (String) data.get("commentId");
                                String commentText = (String) data.get("commentText");
                                boolean isAnonymous = (boolean) data.get("isAnonymous");

                                commentArrayList.add(new Comment(commentSenderId, commentText, isAnonymous, postId));
                            }
                            commentAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }




}