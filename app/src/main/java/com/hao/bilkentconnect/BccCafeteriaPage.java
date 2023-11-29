package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityBccCafeteriaPageBinding;
import com.hao.bilkentconnect.databinding.ActivityMealCommentBinding;

public class BccCafeteriaPage extends AppCompatActivity {
    private ActivityBccCafeteriaPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBccCafeteriaPageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
    }
}