package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public void changePassword(View view) {
        String currentPassword = binding.currentPasswordText.getText().toString();
        String newPassword = binding.newPasswordText.getText().toString();
        String newPasswordConfirm = binding.newPasswordConfirmText.getText().toString();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();


        AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);
        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // User re-authenticated, now you can update the password
                if (newPassword.equals(newPasswordConfirm)) {
                    user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChangePasswordPage.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(ChangePasswordPage.this, "Failed to update password: " + e.getMessage(), Toast.LENGTH_LONG).show());
                } else {
                    Toast.makeText(ChangePasswordPage.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(ChangePasswordPage.this, "Re-authentication failed: " + e.getMessage(), Toast.LENGTH_LONG).show());

    }
}