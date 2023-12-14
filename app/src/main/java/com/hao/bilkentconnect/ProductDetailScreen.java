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
    private void loadProductDetails(String productId) {
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