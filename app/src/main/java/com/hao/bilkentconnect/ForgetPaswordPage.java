package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityForgetPaswordPageBinding;
import com.hao.bilkentconnect.ui.login.LoginActivity;

public class ForgetPaswordPage extends AppCompatActivity {

    private ActivityForgetPaswordPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPaswordPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void goToBackPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}