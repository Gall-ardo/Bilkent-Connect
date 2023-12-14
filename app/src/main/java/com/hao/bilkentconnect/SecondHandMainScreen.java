package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.Adapter.ProductAdapter;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.databinding.ActivitySecondHandMainScreenBinding;

import java.util.ArrayList;

public class SecondHandMainScreen extends AppCompatActivity {


    // yanlis olan aktivite
    private ActivitySecondHandMainScreenBinding binding;
    private ConstraintLayout secondHandLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;
    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondHandMainScreenBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        secondHandLayout = binding.secondHandLayout;
        sideMenu = binding.sideMenu;
        ImageView menuIcon = binding.menuIcon;

        productArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loadProductsFromFirebase();


        menuIcon.setOnClickListener(v -> toggleSideMenu());
        sideMenu.setOnClickListener(v -> onSideMenuItemClick());
        secondHandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSideMenuVisible) {
                    toggleSideMenu();
                }
            }
        });





    }
    private void loadProductsFromFirebase() {

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