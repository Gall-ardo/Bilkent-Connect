package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.OnPostClickListener;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerPostBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> posts;

    private OnPostClickListener listener;

    public PostAdapter(ArrayList<Post> posts, OnPostClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerPostBinding binding = RecyclerPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post currentPost = posts.get(position);

        if (currentPost.isAnonymous) {
            holder.binding.topUsernameText.setText("Ghost");
            holder.binding.profilePicture.setImageResource(R.drawable.ghost_icon); // Replace with your ghost drawable resource

        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = currentPost.sharerId;

            // Fetch user by ID to get the username
            db.collection("Users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null) {
                        // Set the username
                        holder.binding.topUsernameText.setText(user.getUsername());
                        if (user.getProfilePhoto() != null) {
                            Picasso.get().load(user.getProfilePhoto()).into(holder.binding.profilePicture);
                        }else{
                            holder.binding.profilePicture.setImageResource(R.drawable.circle);
                        }
                    }
                }
            });
        }

        holder.binding.saveButton.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userId = currentUser.getUid();
                // Add this post to the user's saved posts
                db.collection("Users").document(userId)
                        .update("savedPosts", FieldValue.arrayUnion(currentPost.getPostDescription())) // Assuming post ID is available
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(holder.itemView.getContext(), "Post saved!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(holder.itemView.getContext(), "Failed to save post: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        holder.binding.descriptionText.setText(currentPost.postDescription);
        Picasso.get().load(currentPost.photoUrl).into(holder.binding.postImage);
        holder.binding.commentButton.setOnClickListener(v -> listener.onPostClick(currentPost));
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public RecyclerPostBinding binding;
        public PostViewHolder(RecyclerPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }



}
