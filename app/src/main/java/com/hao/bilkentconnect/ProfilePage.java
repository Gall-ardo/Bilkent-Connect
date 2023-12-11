package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hao.bilkentconnect.ui.login.LoginActivity;

public class ProfilePage extends AppCompatActivity {




    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

    }




    public void goToMainPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToEditProfilePage(View view){
        Intent intent = new Intent(this, EditProfilePage.class);
        startActivity(intent);
    }

    public void goToYourPostsPage(View view){
        Intent intent = new Intent(this, OwnPostPage.class);
        startActivity(intent);
    }

    public void goToSavedPostsPage(View view){
        Intent intent = new Intent(this, SavedPostPages.class);
        startActivity(intent);
    }

    public void goToSecurityPage(View view){
        Intent intent = new Intent(this, ChangePasswordPage.class);
        startActivity(intent);
    }

    public void goToLogOutPage(View view){

        //TODO burda user'ı log out yapmamız lazım
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


















}