package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.databinding.ActivityBccCafeteriaPageBinding;
import com.hao.bilkentconnect.databinding.ActivityChangePasswordPageBinding;

public class ChangePasswordPage extends AppCompatActivity {
    private ActivityChangePasswordPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordPageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);


    }


    public void goBackToProfilePage(View view){
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }
    public void changePassword(View view){
        String currentPassword = binding.currentPasswordText.getText().toString();
        String newPassword = binding.newPasswordText.getText().toString();
        String newPasswordConfirm = binding.newPasswordConfirmText.getText().toString();
        //
    }
}