package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.MealComment;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerMealCommentsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealCommentAdapter extends RecyclerView.Adapter<MealCommentAdapter.MealCommentViewHolder> {

    private ArrayList<MealComment> mealComments;
    private FirebaseFirestore db;

    public MealCommentAdapter(ArrayList<MealComment> mealComments) {
        this.mealComments = mealComments;
    }

    @NonNull
    @Override
    public MealCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerMealCommentsBinding mealCommentsBinding = RecyclerMealCommentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MealCommentViewHolder(mealCommentsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealCommentViewHolder holder, int position) {
        MealComment comment = mealComments.get(position);
        holder.mealCommentsBinding.commentText.setText(comment.getCommentText());

        if (comment.isAnonymous()) {
            // For anonymous comments
            holder.mealCommentsBinding.profilePicture.setImageResource(R.drawable.ghost_icon); // Ghost icon for anonymous
            holder.mealCommentsBinding.usernameText.setText("Anonymous");
        } else {
            // For non-anonymous comments
            db = FirebaseFirestore.getInstance();
            db.collection("Users").document(comment.getCommentSenderId())
                    .get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            if (user != null) {
                                holder.mealCommentsBinding.usernameText.setText(user.getUsername());
                                if (user.getProfilePhoto() != null && !user.getProfilePhoto().isEmpty()) {
                                    Picasso.get().load(user.getProfilePhoto()).into(holder.mealCommentsBinding.profilePicture);
                                } else {
                                    holder.mealCommentsBinding.profilePicture.setImageResource(R.drawable.profile_icon); // Default icon
                                }
                            }
                        }
                    }).addOnFailureListener(e -> {
                        holder.mealCommentsBinding.profilePicture.setImageResource(R.drawable.profile_icon); // Default icon on failure
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mealComments.size();
    }

    public static class MealCommentViewHolder extends RecyclerView.ViewHolder {
        public RecyclerMealCommentsBinding mealCommentsBinding;

        public MealCommentViewHolder(RecyclerMealCommentsBinding mealCommentsBinding) {
            super(mealCommentsBinding.getRoot());
            this.mealCommentsBinding = mealCommentsBinding;
        }
    }
}