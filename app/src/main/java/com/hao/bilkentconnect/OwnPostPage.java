package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;
import com.hao.bilkentconnect.databinding.ActivityOwnPostPageBinding;

import java.util.ArrayList;

public class OwnPostPage extends AppCompatActivity {
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private ActivityOwnPostPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnPostPageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        postArrayList = new ArrayList<>();

        binding.yourPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.yourPostsRecyclerView.setAdapter(postAdapter);
    }
}