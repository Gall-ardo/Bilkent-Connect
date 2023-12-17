package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hao.bilkentconnect.Adapter.MealCommentAdapter;
import com.hao.bilkentconnect.ModelClasses.MealComment;
import com.hao.bilkentconnect.databinding.ActivityMealCommentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MealCommentActivity extends AppCompatActivity {
    private ActivityMealCommentBinding binding;
    private ArrayList<MealComment> mealCommentArrayList;
    private MealCommentAdapter mealCommentAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealCommentBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get meal details from the intent
        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealType");
        String mealDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Setup meal time text
        int color = intent.getIntExtra("textColor", Color.BLACK);
        binding.mealTimeText.setTextColor(color);
        binding.mealTimeText.setText(mealName);

        // Initialize RecyclerView
        mealCommentArrayList = new ArrayList<>();
        mealCommentAdapter = new MealCommentAdapter(mealCommentArrayList);
        binding.mealCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.mealCommentRecyclerView.setAdapter(mealCommentAdapter);

        // Load comments for the specific meal and date
        loadMealComments(mealDate, mealName);
    }

    private void loadMealComments(String mealDate, String mealType) {
        db.collection("MealComments")
                .whereEqualTo("mealDate", mealDate)
                .whereEqualTo("mealType", mealType)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    mealCommentArrayList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        MealComment comment = documentSnapshot.toObject(MealComment.class);
                        mealCommentArrayList.add(comment);
                    }
                    mealCommentAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(MealCommentActivity.this, "Error loading comments: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void goBackToBccPage(View view) {
        Intent intent = new Intent(this, BccCafeteriaPage.class);
        startActivity(intent);
    }
    public void shareRegularMealComment(View view) {
        String commentText = binding.commentInputEditText.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String mealType = getIntent().getStringExtra("mealType");
        String mealDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Create a new MealComment object using the constructor
        MealComment newComment = new MealComment(UUID.randomUUID().toString(), userId, commentText, false, mealDate, mealType, new Date());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("MealComments").document(newComment.getCommentId()).set(newComment)
                .addOnSuccessListener(aVoid -> {
                    binding.commentInputEditText.setText("");
                    loadMealComments(mealDate, mealType);
                    Toast.makeText(this, "Comment added successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error adding comment: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    public void shareAnonymousMealComment(View view) {
        String commentText = binding.commentInputEditText.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        String mealType = getIntent().getStringExtra("mealType");
        String mealDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        MealComment newComment = new MealComment(
                UUID.randomUUID().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),commentText, true, mealDate, mealType, new Date());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("MealComments").document(newComment.getCommentId()).set(newComment)
                .addOnSuccessListener(aVoid -> {
                    binding.commentInputEditText.setText("");
                    loadMealComments(mealDate, mealType);
                    Toast.makeText(this, "Anonymous comment added successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error adding comment: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }



}
