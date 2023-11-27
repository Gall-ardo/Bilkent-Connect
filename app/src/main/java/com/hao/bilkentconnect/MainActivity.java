package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoMainFragment();
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
        fragmentTransaction.replace(R.id.frame_layout, mainFlowFragment).commit();

    }
    //
    public void gotoProfilePage() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfilePageFragment profilePageFragment = new ProfilePageFragment();
        //fragmentTransaction.add(R.id.frame_layout, profilePage).commit();
        fragmentTransaction.replace(R.id.frame_layout, profilePageFragment).commit();


    }
    public void gotoSavedPosts() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SavedPostsFragment savedPostsFragment = new SavedPostsFragment();
        //fragmentTransaction.add(R.id.frame_layout, savedPostsFragment).commit();
        fragmentTransaction.replace(R.id.frame_layout, savedPostsFragment).commit();


    }
    public void gotoOwnPosts() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OwnPostFragment ownPostFragment = new OwnPostFragment();
        //fragmentTransaction.add(R.id.frame_layout, ownPostFragment).commit();
        fragmentTransaction.replace(R.id.frame_layout, ownPostFragment).commit();


    }
}