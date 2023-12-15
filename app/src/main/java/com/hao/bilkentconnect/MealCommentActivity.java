package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.Comment;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityMealCommentBinding;

import java.util.ArrayList;

public class MealCommentActivity extends AppCompatActivity {
    private ActivityMealCommentBinding binding;
    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealCommentBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        //Colorful meal time text
        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealType");
        int color = intent.getIntExtra("textColor", Color.BLACK);
        binding.mealTimeText.setTextColor(color);
        binding.mealTimeText.setText(mealName);

        commentArrayList = new ArrayList<>();

        binding.mealCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentArrayList);
        binding.mealCommentRecyclerView.setAdapter(commentAdapter);
    }

    public void goBackToBccPage(View view) {
        Intent intent = new Intent(this, BccCafeteriaPage.class);
        startActivity(intent);
    }
}