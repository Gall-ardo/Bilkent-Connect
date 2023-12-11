package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

public class ProfilePage extends AppCompatActivity {



    ImageView user_photo;
    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        user_photo = findViewById(R.id.user_imageView);
    }
}