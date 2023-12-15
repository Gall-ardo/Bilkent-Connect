package com.hao.bilkentconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.PostAdapter;
import com.hao.bilkentconnect.Adapter.ProductAdapter;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityOwnHandScreenBinding;

import java.util.ArrayList;
import java.util.Map;

public class OwnHandScreen extends AppCompatActivity implements OnProductClickListener{

    private ActivityOwnHandScreenBinding binding;
    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnHandScreenBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        productArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        loadProductsFromFirebase();


        binding.ownHandRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productArrayList, this, true);
        binding.ownHandRecyclerView.setAdapter(productAdapter);
    }
    private void loadProductsFromFirebase() {
        CollectionReference collectionReference = db.collection("Products");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Log.e("Firestore Error", error.getMessage());
                    Toast.makeText(OwnHandScreen.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

                        if (product != null && product.getSeller().equals(currentUserId)) {
                            productArrayList.add(product);
                        }
                    }
                    productAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    public void goToSecondHandSalePage(View view){
        Intent intent = new Intent(this, SecondHandMain.class);
        startActivity(intent);
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailScreen.class);
        intent.putExtra("productDescription", product.getDescription());
        intent.putExtra("productPrice", product.getPrice());
        intent.putExtra("productTitle", product.getProductName());
        intent.putExtra("productImage", product.getImage());
        startActivity(intent);
    }
}