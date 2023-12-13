package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;
import com.hao.bilkentconnect.databinding.ActivitySavedPostPagesBinding;

import java.util.ArrayList;

public class SavedPostPages extends AppCompatActivity implements OnPostClickListener{

    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
    private ActivitySavedPostPagesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedPostPagesBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        postArrayList = new ArrayList<>();

        binding.savedPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList, this);
        binding.savedPostsRecyclerView.setAdapter(postAdapter);
    }

    @Override
    public void onPostClick(Post post) {
        Intent intent = new Intent(this, PostView.class);
        intent.putExtra("post_id", post.getPostId()); // Pass the post ID
        startActivity(intent);
    }
}