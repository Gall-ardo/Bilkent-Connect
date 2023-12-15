package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Comment;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerCommentsBinding;
import com.hao.bilkentconnect.databinding.RecyclerPostBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private ArrayList<Comment> comments;

    public CommentAdapter(ArrayList<Comment> comments)  {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerCommentsBinding commentsBinding = RecyclerCommentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHolder(commentsBinding);
    }

    /*@Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        /*holder.commentsBinding.commentText.setText("fdalkbjfbdalkj");
        holder.commentsBinding.usernameText.setText("anan");
        holder.commentsBinding.profilePicture.setImageResource(R.drawable.cropped_profile_photo);*/

        /*holder.commentsBinding.commentText.setText(comment.getCommentText());

        if (comment.isAnonymous()) {
            holder.commentsBinding.profilePicture.setVisibility(View.VISIBLE); // Hide profile picture for anonymous comments
            holder.commentsBinding.profilePicture.setImageResource(R.drawable.ghost_icon);
            holder.commentsBinding.usernameText.setText("Ghost");
        } else {
            holder.commentsBinding.profilePicture.setVisibility(View.VISIBLE);
            holder.commentsBinding.profilePicture.setImageResource(R.drawable.cropped_profile_photo);
        }

    }*/
        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            Comment comment = comments.get(position);

            holder.commentsBinding.commentText.setText(comment.getCommentText());

            if (comment.isAnonymous()) {
                holder.commentsBinding.profilePicture.setImageResource(R.drawable.ghost_icon); // Ghost icon for anonymous
                holder.commentsBinding.usernameText.setText("Ghost");
            } else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(comment.getCommentSender())
                        .get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                User user = documentSnapshot.toObject(User.class);
                                if (user != null) {
                                    holder.commentsBinding.usernameText.setText(user.getUsername());
                                    if (user.getProfilePhoto() != null && !user.getProfilePhoto().isEmpty()) {
                                        Picasso.get().load(user.getProfilePhoto()).into(holder.commentsBinding.profilePicture);
                                    } else {
                                        holder.commentsBinding.profilePicture.setImageResource(R.drawable.profile_icon);
                                    }
                                }
                            }
                        }).addOnFailureListener(e -> {
                            holder.commentsBinding.profilePicture.setImageResource(R.drawable.profile_icon);
                        });
            }
        }





    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        public RecyclerCommentsBinding commentsBinding;
        public CommentViewHolder(RecyclerCommentsBinding commentsBinding) {
            super(commentsBinding.getRoot());
            this.commentsBinding = commentsBinding;
        }
    }
}
