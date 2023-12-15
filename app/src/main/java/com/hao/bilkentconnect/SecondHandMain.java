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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.Adapter.ProductAdapter;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.databinding.ActivityOwnHandScreenBinding;
import com.hao.bilkentconnect.databinding.ActivitySecondHandMainBinding;

import java.util.ArrayList;
import java.util.Map;

public class SecondHandMain extends AppCompatActivity implements OnProductClickListener{

    // gercek olan

    private ArrayList<Product> productArrayList;
    private ProductAdapter productAdapter;
    private ActivitySecondHandMainBinding binding;
    private ConstraintLayout secondHandLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;
    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondHandMainBinding.inflate(getLayoutInflater());
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

        binding.recyclerViewSecondHand.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productArrayList, this);
        binding.recyclerViewSecondHand.setAdapter(productAdapter);

    }

    private void loadProductsFromFirebase() {
        CollectionReference collectionReference = db.collection("Products");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Log.e("Firestore Error", error.getMessage());
                    Toast.makeText(SecondHandMain.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {
                    productArrayList.clear(); // Clear existing data
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        String productId = snapshot.getId();
                        String productName = (String) data.get("productName");
                        String image = (String) data.get("image");
                        String description = (String) data.get("description");
                        String seller = (String) data.get("seller");
                        String price = ((String) data.get("price"));

                        Product product = new Product(productId, productName, image, description, seller, price);
                        productArrayList.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
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

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailScreen.class);
        intent.putExtra("productId", product.getProductId()); // Pass the product ID
        startActivity(intent);
    }

    public void goToShareAnnouncementPage(View view){
        Intent intent = new Intent(this, ShareAnnoucementScreen.class);
        startActivity(intent);
    }

    public void goToOwnHandPage(View view){
        Intent intent = new Intent(this, OwnHandScreen.class);
        startActivity(intent);
    }

    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToBCCDailyPage(View view){
        Intent intent = new Intent(this, BccCafeteriaPage.class);
        startActivity(intent);
    }
}