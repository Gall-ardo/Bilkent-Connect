package com.hao.bilkentconnect.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.OnProductClickListener;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerProductsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private ArrayList<Product> products;
    public OnProductClickListener listener;
    private boolean isButtonVisible;


    public ProductAdapter(ArrayList<Product> products, OnProductClickListener listener, boolean isButtonVisible) {
        this.products = products;
        this.listener = listener;
        this.isButtonVisible = isButtonVisible;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerProductsBinding productsBinding = RecyclerProductsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(productsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (isButtonVisible) {
            holder.productsBinding.eraseProductCross.setVisibility(View.VISIBLE);
        } else {
            holder.productsBinding.eraseProductCross.setVisibility(View.GONE);
        }
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
        holder.productsBinding.productInfoTable.setOnClickListener(v -> listener.onProductClick(currentProduct));

        holder.productsBinding.eraseProductCross.setOnClickListener(v -> {
            String productId = currentProduct.getProductId();
            deleteProduct(holder, productId, position);
        });
    }

    public void updateList(ArrayList<Product> newList) {
        products.clear();
        products.addAll(newList);
        notifyDataSetChanged();
    }

    private void deleteProduct(ProductViewHolder holder, String productId, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    products.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(holder.productsBinding.getRoot().getContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(holder.productsBinding.getRoot().getContext(), "Error deleting product: " + e.getMessage(), Toast.LENGTH_LONG).show());
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