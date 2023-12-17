package com.hao.bilkentconnect.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.OnPostClickListener;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerPostBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> posts;

    private OnPostClickListener listener;

    public PostAdapter(ArrayList<Post> posts, OnPostClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //fix herhangi bir şey yok
        RecyclerPostBinding binding = RecyclerPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post currentPost = posts.get(position);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = currentPost.sharerId;

        if (currentPost.isAnonymous) {
            holder.binding.topUsernameText.setText("Ghost");
            holder.binding.profilePicture.setImageResource(R.drawable.ghost_icon); // Replace with your ghost drawable resource

        } else {

            // Fetch user by ID to get the username
            db.collection("Users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Map<String, Object> userData = documentSnapshot.getData();
                    if (userData != null) {
                        String username = (String) userData.get("username");
                        String profilePhoto = (String) userData.get("profilePhoto");
                        List<String> likedPosts = (List<String>) userData.get("likedPosts");

                        // Set the username and profile picture
                        holder.binding.topUsernameText.setText(username);
                        if (profilePhoto != null) {
                            Picasso.get().load(profilePhoto).into(holder.binding.profilePicture);
                        } else {
                            holder.binding.profilePicture.setImageResource(R.drawable.circle);
                        }
                    }
                }
            });
        }
        db.collection("Users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    // Check if the liked posts contain the current post ID
                    if (user.getLikedPosts().contains(currentPost.getPostId())) {
                        holder.binding.likeButton.setImageResource(R.drawable.filled_like_button);
                        holder.binding.likeButton.setEnabled(false);
                    } else {
                        holder.binding.likeButton.setImageResource(R.drawable.like_button);
                        holder.binding.likeButton.setEnabled(true);
                    }
                }
            }
        }).addOnFailureListener(e -> {
            // Handle any errors here
            Log.e("UserDataFetch", "Error fetching user data", e);
        });



        holder.binding.likeButton.setOnClickListener(v -> {
            String postId = currentPost.getPostId(); // Assuming 'currentPost' is the post object for this item

            DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

            userRef.get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                if (user != null && !user.getLikedPosts().contains(postId)) {
                    userRef.update("likedPosts", FieldValue.arrayUnion(postId));
                    holder.binding.likeButton.setImageResource(R.drawable.filled_like_button);
                    holder.binding.likeButton.setEnabled(false); // Disable the button after like
                }
            }).addOnFailureListener(e -> {
                // Handle any errors here
                Log.e("LikeButton", "Error updating like status", e);
            });
        });


        holder.binding.descriptionText.setText(currentPost.postDescription);
        Picasso.get().load(currentPost.photoUrl).into(holder.binding.postImage);
        holder.binding.commentButton.setOnClickListener(v -> listener.onPostClick(currentPost));
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder { // fix burda durum yok
        public RecyclerPostBinding binding;
        public PostViewHolder(RecyclerPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }



}
