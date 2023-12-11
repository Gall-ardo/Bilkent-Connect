package com.hao.bilkentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivitySecondHandMainScreenBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ConstraintLayout mainLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PostAdapter postAdapter = new PostAdapter(posts);
        binding.recyclerView.setAdapter(postAdapter);

        mainLayout = findViewById(R.id.mainLayout);
        sideMenu = findViewById(R.id.sideMenu);

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> toggleSideMenu());
        sideMenu.setOnClickListener(v -> onSideMenuItemClick());
        mainLayout.setOnClickListener(new View.OnClickListener() {
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

    // altttaki butonlara tiklayinca olusacaklar
    public void gotoChatPage() {

    }
    public void sharePost() {

    }
    public void gotoMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFlowFragment mainFlowFragment = new MainFlowFragment();
        //fragmentTransaction.replace(R.id.frame_layout, mainFlowFragment).commit();

    }
    //
    public void gotoProfilePage() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfilePageFragment profilePageFragment = new ProfilePageFragment();
        //fragmentTransaction.add(R.id.frame_layout, profilePage).commit();
        //fragmentTransaction.replace(R.id.frame_layout, profilePageFragment).commit();


    }
    public void gotoSavedPosts() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SavedPostsFragment savedPostsFragment = new SavedPostsFragment();
        //fragmentTransaction.add(R.id.frame_layout, savedPostsFragment).commit();
        //fragmentTransaction.replace(R.id.frame_layout, savedPostsFragment).commit();


    }
    public void gotoOwnPosts() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OwnPostFragment ownPostFragment = new OwnPostFragment();
        //fragmentTransaction.add(R.id.frame_layout, ownPostFragment).commit();
        //fragmentTransaction.replace(R.id.frame_layout, ownPostFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // burada menuyu baglama isini yapcaagiz
        // ornek olarak:
        //MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(binding.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // opsiyonlardan biri sevilirse ne olacak

        //if (item.getItemId() == binding.bccDaily){ //go to bcc daily
        // Intent intent = new Intent(this, BccCafeteriaPage.class);
        // startActivity(intent);
        // }
        //if (item.getItemId() == binding.second){ go to seoncd hand}
        //




        return super.onOptionsItemSelected(item);
    }


    public void goToProfile_2(View view){

        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);









    }




}
