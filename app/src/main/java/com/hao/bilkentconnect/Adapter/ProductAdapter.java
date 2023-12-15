package com.hao.bilkentconnect.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.OnProductClickListener;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerProductsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private ArrayList<Product> products;
    public OnProductClickListener listener;

    public ProductAdapter(ArrayList<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerProductsBinding productsBinding = RecyclerProductsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(productsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = products.get(position);

        holder.productsBinding.productTitle.setText(currentProduct.getProductName());
        holder.productsBinding.productPrice.setText(String.valueOf(currentProduct.getPrice()));

        // Load product image using Picasso
        if (currentProduct.getImage() != null && !currentProduct.getImage().isEmpty()) {
            Picasso.get().load(currentProduct.getImage()).into(holder.productsBinding.productImage);
        } else {
            // Set a default image if no image URL is available
            holder.productsBinding.productImage.setImageURI(null);
            Toast.makeText(holder.productsBinding.getRoot().getContext(), "No image URL available", Toast.LENGTH_SHORT).show();
        }

        holder.productsBinding.productPrice.setOnClickListener(v -> listener.onProductClick(currentProduct));
        holder.productsBinding.productImage.setOnClickListener(v -> listener.onProductClick(currentProduct));
        holder.productsBinding.productTitle.setOnClickListener(v -> listener.onProductClick(currentProduct));
        //holder.productsBinding.productInfoTable.setOnClickListener(v -> listener.onProductClick(currentProduct));
        //        holder.binding.commentButton.setOnClickListener(v -> listener.onPostClick(currentPost));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public RecyclerProductsBinding productsBinding;
        public ProductViewHolder(RecyclerProductsBinding productsBinding) {
            super(productsBinding.getRoot());
            this.productsBinding = productsBinding;
        }
    }

}