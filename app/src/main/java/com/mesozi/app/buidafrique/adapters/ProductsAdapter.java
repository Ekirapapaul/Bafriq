package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.ProductCategory;
import com.mesozi.app.buidafrique.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/4/18 .
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsHolder> {
    private List<ProductCategory> products = new ArrayList<>();
    private Context context;

    public ProductsAdapter(Context context, List<ProductCategory> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_single_card, parent, false);
        return new ProductsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int position) {
        ProductCategory product = products.get(position);
        holder.textView.setText(product.getName());
    }

    public ProductCategory getSelected(int position) {
        return products.get(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductsHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ProductsHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
