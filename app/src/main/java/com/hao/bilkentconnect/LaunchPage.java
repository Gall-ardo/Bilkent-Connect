package com.hao.bilkentconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.ui.login.LoginActivity;

public class LaunchPage extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_page); // Replace with your layout file

        // Additional setup or initialization if needed

        // Using a Handler to delay the start of the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity after the splash delay
                startNextActivity();
            }
        }, SPLASH_DELAY);
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, LoginActivity.class); // Replace with your target activity
        startActivity(intent);
        finish(); // Finish the launch activity to prevent going back to it
    }
}