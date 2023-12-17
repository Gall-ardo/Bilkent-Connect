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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.User;
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
    /*public void signUpClicked(View view) {
        String email = binding.enterMail.getText().toString();
        String password = binding.enterPassword.getText().toString();
        String userName = binding.username.getText().toString();
        String passwordAgain = binding.enterPasswordAgain.getText().toString();

        if(!password.equals(passwordAgain)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return;
        }

        if(email.isEmpty() || password.isEmpty() || userName.isEmpty() || passwordAgain.isEmpty()) {
            Toast.makeText(this, "Please enter email, password, and username!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // After successful registration, create user profile in Firestore
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                User newUser = new User(firebaseUser.getUid(), email, userName);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(firebaseUser.getUid()).set(newUser)
                        .addOnSuccessListener(aVoid -> {
                            // Navigate to MainActivity
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }*/
    public void goToLoginPage(View view) {
        Intent intent = new Intent(Register.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }
    public void signUpClicked(View view) {
        String email = binding.enterMail.getText().toString();
        String password = binding.enterPassword.getText().toString();
        String userName = binding.username.getText().toString();
        String passwordAgain = binding.enterPasswordAgain.getText().toString();

        if(!password.equals(passwordAgain)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return;
        }

        if(email.isEmpty() || password.isEmpty() || userName.isEmpty() || passwordAgain.isEmpty()) {
            Toast.makeText(this, "Please enter email, password, and username!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                User newUser = new User(firebaseUser.getUid(), email, userName);
                //newUser.setProfilePhoto("https://firebasestorage.googleapis.com/v0/b/bilkentconnect-344eb.appspot.com/o/default%20pp.png?alt=media&token=c8bba3a4-34aa-4122-b909-cffc7026128a");

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(firebaseUser.getUid()).set(newUser)
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
            }
        }).addOnFailureListener(e ->
                Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }


}