package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityChatBinding;
import com.hao.bilkentconnect.databinding.ActivityEditProfilePageBinding;

public class EditProfilePage extends AppCompatActivity {
    private ActivityEditProfilePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfilePageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
    }
}