package com.mesozi.app.buidafrique.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesozi.app.buidafrique.Models.ProductCategory;
import com.mesozi.app.buidafrique.Models.ProductCategory_Table;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.adapters.ProductsAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Objects;

/**
 * Created by ekirapa on 9/4/18 .
 */
public class ProductsFrag extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_products, container, false);
        int tag = Objects.requireNonNull(getArguments()).getInt("tag");
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ProductCategory> productCategories = SQLite.select().from(ProductCategory.class).where(ProductCategory_Table.categoryId.eq(tag)).queryList();

        final ProductsAdapter productsAdapter = new ProductsAdapter(getContext(), productCategories);
        recyclerView.setAdapter(productsAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ProductCategory product = productsAdapter.getSelected(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getUrl()));
//                startActivity(browserIntent);
            }
        }));
        return view;
    }
}
