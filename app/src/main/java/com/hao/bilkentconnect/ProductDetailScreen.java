package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityProductDetailScreenBinding;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicReference;

public class ProductDetailScreen extends AppCompatActivity {

    private ActivityProductDetailScreenBinding binding;

    private FirebaseFirestore firebaseFirestore;
    private String sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailScreenBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        //loadProductDetails(productId);
        firebaseFirestore = FirebaseFirestore.getInstance();

        String description = getIntent().getStringExtra("productDescription");
        String price = getIntent().getStringExtra("productPrice");
        String title = getIntent().getStringExtra("productTitle");
        String image = getIntent().getStringExtra("productImage");

        sellerId = getIntent().getStringExtra("sellerId");

        binding.descriptionText.setText(description);
        binding.productPrice.setText(price);
        binding.productTitle.setText(title);
        Picasso.get().load(image).into(binding.productImage);
    }


    /*private void loadProductDetails(String productId) {
        System.out.println("Checkpoint 1 loading product details");
        firebaseFirestore.collection("Products").document(productId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    System.out.println("Checkpoint 2 loading product details");
                    if (documentSnapshot.exists()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        String productName = (String) data.get("productName");
                        String image = (String) data.get("image");
                        String description = (String) data.get("description");
                        String seller = (String) data.get("seller");
                        String price = ((String) data.get("price"));
                        Product product = new Product(productName, image, description, seller, price);

                        if (product != null) {
                            binding.descriptionText.setText(product.getDescription());
                            binding.productPrice.setText(String.valueOf(product.getPrice()));
                            binding.productTitle.setText(product.getProductName());
                            Picasso.get().load(product.getImage()).into(binding.productImage);

                            firebaseFirestore.collection("Users").document(product.getSeller())
                                    .get().addOnSuccessListener(sellerSnapshot -> {
                                        if (sellerSnapshot.exists()) {
                                            User sellerUser = sellerSnapshot.toObject(User.class);
                                            if (sellerUser != null) {
                                                // Display the seller's username or other details
                                                // Example: binding.sellerUsername.setText(sellerUser.getUsername());
                                            }
                                        }
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(this, "Error loading seller details", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading product details", Toast.LENGTH_SHORT).show());
    }*/

    public void goToSecondHandMain(View view) {
        Intent intent = new Intent(this, SecondHandMain.class);
        startActivity(intent);
        finish();
    }
    public void MakeContactMethod(View view) {



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String curr_ıd = firebaseAuth.getCurrentUser().getUid(); // curr user_id

        Chat.createChat(this, firebaseFirestore, curr_ıd, sellerId);


        Intent intent = new Intent(ProductDetailScreen.this, ChatActivity.class);
        startActivity(intent);
        finish();

    }
}