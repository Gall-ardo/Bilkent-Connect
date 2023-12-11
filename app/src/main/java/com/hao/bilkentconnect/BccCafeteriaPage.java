package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hao.bilkentconnect.databinding.ActivityBccCafeteriaPageBinding;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityMealCommentBinding;

public class BccCafeteriaPage extends AppCompatActivity {
    private ActivityBccCafeteriaPageBinding binding;
    private ConstraintLayout bccLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBccCafeteriaPageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        bccLayout = findViewById(R.id.bccLayout);
        sideMenu = findViewById(R.id.sideMenu);

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> toggleSideMenu());
        sideMenu.setOnClickListener(v -> onSideMenuItemClick());
        bccLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSideMenuVisible) {
                    toggleSideMenu();
                }
            }
        });

        //gotoMainFragment();
    }

    private void onSideMenuItemClick() {
        // Handle the click event for side menu items
        Log.d("ClickEvent", "Clicked on: ");

        // Close the side menu after handling the click
        toggleSideMenu();
    }

    public void toggleSideMenu() {
        isSideMenuVisible = !isSideMenuVisible;

        float fromXDelta, toXDelta;

        if (isSideMenuVisible) {
            fromXDelta = -1f; // Start from the left edge
            toXDelta = 0f;
        } else {
            fromXDelta = 0f;
            toXDelta = -1f; // Move back to the left edge
        }

        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromXDelta,
                Animation.RELATIVE_TO_SELF, toXDelta,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
        );

        animation.setDuration(300);
        sideMenu.startAnimation(animation);

        int visibility = isSideMenuVisible ? View.VISIBLE : View.INVISIBLE;
        sideMenu.setVisibility(visibility);
    }
}