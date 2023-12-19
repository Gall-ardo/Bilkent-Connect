package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;
import com.hao.bilkentconnect.databinding.ActivitySavedPostPagesBinding;

import java.util.ArrayList;

public class SavedPostPages extends AppCompatActivity implements OnPostClickListener{

    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private ActivitySavedPostPagesBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedPostPagesBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        postArrayList = new ArrayList<>();
        postAdapter = new PostAdapter(postArrayList, this);
        binding.savedPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.savedPostsRecyclerView.setAdapter(postAdapter);

        loadLikedPosts();
    }

    private void loadLikedPosts() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return;
        }

        String userId = currentUser.getUid();
        db.collection("Users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if (user != null && user.getLikedPosts() != null) {
                for (String postId : user.getLikedPosts()) {
                    db.collection("Posts").document(postId).get().addOnSuccessListener(postSnapshot -> {
                        Post post = postSnapshot.toObject(Post.class);
                        if (post != null) {
                            postArrayList.add(post);
                            postAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(SavedPostPages.this, "Error loading liked posts", Toast.LENGTH_SHORT).show());
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