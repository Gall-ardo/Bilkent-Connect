package com.hao.bilkentconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.ModelClasses.Comment;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ConstraintLayout mainLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;
    public FirebaseFirestore db;
    ArrayList<Post> postArrayList;
    PostAdapter postAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        db = FirebaseFirestore.getInstance();
        loadPostsFromFirebase();


        postArrayList = new ArrayList<>();

        mainLayout = binding.mainLayout;
        sideMenu = binding.sideMenu;

        ImageView menuIcon = binding.menuIcon;
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


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);



    }
    private void loadPostsFromFirebase() {

        CollectionReference collectionReference = db.collection("Posts");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if(error!=null) {
                    Log.e("Firestore Error", error.getMessage());
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getData();
                        String postId = (String) data.get("postId");
                        String sharerId = (String) data.get("sharerId");
                        int likeCount = (int) data.get("likeCount");
                        boolean isAnonymous = (boolean) data.get("isAnonymous");
                        String photoUrl = (String) data.get("photoUrl");
                        String postDescription = (String) data.get("postDescription");
                        ArrayList<Comment> comments = (ArrayList<Comment>) data.get("comments");
                        Date timestamp = (Date) data.get("timestamp");
                        Post post = new Post(postId, sharerId, likeCount, isAnonymous, photoUrl, postDescription, comments, timestamp);

                        postArrayList.add(post);
                    }
                    postAdapter.notifyDataSetChanged();
                }
            }
        });
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

    public void gotoChatPage() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);

    }
    public void goToSharePostActivity(View view){
        Intent intent = new Intent(this, SharePostsActivity.class);
        startActivity(intent);
    }

    public void goToProfilePage(View view){
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }

    public void resetMainPage(View view){

    }
    public void onSideMenuItemClick(View view) {


    }
    public void goToSecondHandSalePage(View view){
        Intent intent = new Intent(this, SecondHandMainScreen.class);
        startActivity(intent);
    }

    public void goToBCCDailyPage(View view){
        Intent intent = new Intent(this, BccCafeteriaPage.class);
        startActivity(intent);
    }


}
