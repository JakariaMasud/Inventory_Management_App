package com.example.programmingtestapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.programmingtestapplication.databinding.SingleProductBinding;
import com.example.programmingtestapplication.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> productList;
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        SingleProductBinding binding= SingleProductBinding.inflate(inflater,parent,false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.binding.productNameTV.setText("Product Name : "+productList.get(position).getProductName());
        holder.binding.unitTV.setText("Unit : "+productList.get(position).getUnitInMilli()+" Milliliter" );
        holder.binding.availableTV.setText("Available Item  : "+productList.get(position).getQuantity()+" Pcs" );

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        SingleProductBinding binding;

        public ProductViewHolder(@NonNull SingleProductBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
