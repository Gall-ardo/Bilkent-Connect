package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.ProductAdapter;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityOwnHandScreenBinding;

import java.util.ArrayList;

public class OwnHandScreen extends AppCompatActivity {

    private ArrayList<Product> productsArrayList;
    private ProductAdapter productAdapter;
    private ActivityOwnHandScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnHandScreenBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        productsArrayList = new ArrayList<>();

        /*binding.ownHandRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productsArrayList);
        binding.ownHandRecyclerView.setAdapter(productAdapter);*/
    }

    public void goToSecondHandSalePage(View view){
        Intent intent = new Intent(this, SecondHandMain.class);
        startActivity(intent);
    }
}