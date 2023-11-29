package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityShareAnnoucementScreenBinding;

public class ShareAnnoucementScreen extends AppCompatActivity {

    private ActivityShareAnnoucementScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareAnnoucementScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}