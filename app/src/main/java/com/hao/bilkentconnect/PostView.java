package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Comment;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityPostViewBinding;

import java.util.ArrayList;

public class PostView extends AppCompatActivity {

    private ActivityPostViewBinding binding;
    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostViewBinding.inflate(getLayoutInflater());

        commentArrayList = new ArrayList<>();
        commentArrayList.add(new Comment(new User(), 22203359, "Hello World", false));
        commentArrayList.add(new Comment(new User(), 22203359, "Hello World", false));
        commentArrayList.add(new Comment(new User(), 22203359, "Hello World", false));
        commentArrayList.add(new Comment(new User(), 22203359, "Hello World", false));
        commentArrayList.add(new Comment(new User(), 22203359, "Hello World", false));


        binding.recyclerCommentView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentArrayList);
        binding.recyclerCommentView.setAdapter(commentAdapter);
    }
}