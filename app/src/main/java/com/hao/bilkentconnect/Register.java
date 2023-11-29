package com.hao.bilkentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hao.bilkentconnect.databinding.ActivityRegisterBinding;
import com.hao.bilkentconnect.ui.login.LoginActivity;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        mAuth = FirebaseAuth.getInstance();
    }
    // creating user with email and password however user id does not adding??
    public void signUpClicked(View view) {
        String email = binding.enterMail.getText().toString();
        String password = binding.enterPassword.getText().toString();
        String userName = binding.username.getText().toString();
        if(email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            Toast.makeText(this, "Please enter email, password, and username!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
    }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void goToLoginPage(View view) {
        Intent intent = new Intent(Register.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

}