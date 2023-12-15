package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityProductDetailScreenBinding;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProductDetailScreen extends AppCompatActivity {

    private ActivityProductDetailScreenBinding binding;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailScreenBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        String productId = getIntent().getStringExtra("productId");
        loadProductDetails(productId);
        firebaseFirestore = FirebaseFirestore.getInstance();


    }
    /*private void loadProductDetails(String productId) {
        firebaseFirestore.collection("Products").document(productId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Product product = documentSnapshot.toObject(Product.class);
                if (product != null) {
                    binding.descriptionText.setText(product.getDescription());
                    binding.productPrice.setText(product.getPrice());
                    binding.productTitle.setText(product.getProductName());
                    Picasso.get().load(product.getImage()).into(binding.productImage);

                    // Fetch the username using the sharerId
                    firebaseFirestore.collection("Users").document(product.getSeller())
                            .get().addOnSuccessListener(userSnapshot -> {
                                if (userSnapshot.exists()) {
                                    User user = userSnapshot.toObject(User.class);
                                    if (user != null) {
                                       // take user to make contact
                                    }
                                }
                            });
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Error loading post", Toast.LENGTH_SHORT).show());
    }*/
    private void loadProductDetails(String productId) {
        firebaseFirestore.collection("Products").document(productId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        if (data != null) {
                            String productName = (String) data.get("productName");
                            String image = (String) data.get("image");
                            String description = (String) data.get("description");
                            String sellerId = (String) data.get("seller");
                            String price = (String) data.get("price");

                            binding.descriptionText.setText(description);
                            binding.productPrice.setText(price);
                            binding.productTitle.setText(productName);
                            Picasso.get().load(image).into(binding.productImage);

                            /*// Fetch the seller's details
                            firebaseFirestore.collection("Users").document(sellerId)
                                    .get().addOnSuccessListener(sellerSnapshot -> {
                                        if (sellerSnapshot.exists()) {
                                            User sellerUser = sellerSnapshot.toObject(User.class);
                                            if (sellerUser != null) {
                                                // Display the seller's username or other details
                                                // For example, updating a TextView in your layout
                                                // binding.sellerUsername.setText(sellerUser.getUsername());
                                            }
                                        }
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(this, "Error loading seller details", Toast.LENGTH_SHORT).show();
                                    });*/
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading product details", Toast.LENGTH_SHORT).show());
    }
    public void goToSecondHandMain(View view) {
        Intent intent = new Intent(this, SecondHandMain.class);
        startActivity(intent);
        finish();
    }
    public void MakeContactMethod(View view) {
        // to do
    }
}