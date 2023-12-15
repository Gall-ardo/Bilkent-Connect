package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;
import com.hao.bilkentconnect.databinding.ActivityOwnPostPageBinding;

import java.util.ArrayList;

public class OwnPostPage extends AppCompatActivity implements OnPostClickListener{
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private ActivityOwnPostPageBinding binding;
    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnPostPageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        postArrayList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadPostsFromFirebase();

        binding.yourPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList, this);
        binding.yourPostsRecyclerView.setAdapter(postAdapter);
    }
    private void loadPostsFromFirebase() {
        db.collection("Posts")
                .whereEqualTo("userId", currentUserId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    postArrayList.clear();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Post post = snapshot.toObject(Post.class);
                        if (post != null) {
                            postArrayList.add(post);
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle any errors here
                    Toast.makeText(OwnPostPage.this, "Error loading posts: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Firestore Error", e.getMessage());
                });
    }


    @Override
    public void onPostClick(Post post) {
        Intent intent = new Intent(this, PostView.class);
        intent.putExtra("post_id", post.getPostId()); // Pass the post ID
        startActivity(intent);
    }

    public void goToProfilePage(View view){
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
        finish();
    }

}