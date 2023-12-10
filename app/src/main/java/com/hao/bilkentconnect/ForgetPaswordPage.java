package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityForgetPaswordPageBinding;

public class ForgetPaswordPage extends AppCompatActivity {

    private ActivityForgetPaswordPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPaswordPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}