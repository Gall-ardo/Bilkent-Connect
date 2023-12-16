package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityProfilePageForOthersBinding;

public class ProfilePageForOthers extends AppCompatActivity {

    private ActivityProfilePageForOthersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageForOthersBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
    }



    
}