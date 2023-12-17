package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hao.bilkentconnect.databinding.ActivityForgetPaswordPageBinding;
import com.hao.bilkentconnect.ui.login.LoginActivity;

public class ForgetPaswordPage extends AppCompatActivity {

    private ActivityForgetPaswordPageBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPaswordPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view) {
        String email = binding.mailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!email.endsWith("@ug.bilkent.edu.tr")) {
            Toast.makeText(this, "Please use a Bilkent email address!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ForgetPaswordPage.this, "Reset link sent to your email", Toast.LENGTH_LONG).show();
                    // Redirect to LoginActivity after successful email send
                    Intent intent = new Intent(ForgetPaswordPage.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(ForgetPaswordPage.this, "Failed to send reset email: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }


    public void goToBackPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}