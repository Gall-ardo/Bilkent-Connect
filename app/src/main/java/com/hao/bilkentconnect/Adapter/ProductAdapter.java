package com.hao.bilkentconnect.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.OnProductClickListener;
import com.hao.bilkentconnect.databinding.RecyclerProductsBinding;
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
        /*holder.productsBinding.productImage.setImageURI();
        holder.productsBinding.productTitle.setText();
        holder.productsBinding.productPrice.setText();*/



        holder.productsBinding.productImage.setOnClickListener(v -> listener.onProductClick(currentProduct));
        holder.productsBinding.productTitle.setOnClickListener(v -> listener.onProductClick(currentProduct));
        holder.productsBinding.productPrice.setOnClickListener(v -> listener.onProductClick(currentProduct));
        holder.productsBinding.productInfoTable.setOnClickListener(v -> listener.onProductClick(currentProduct));
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