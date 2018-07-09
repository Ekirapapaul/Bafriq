package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mesozi.app.buidafrique.Models.BaseItem;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/9/18 .
 */
public class ItemActivity extends AppCompatActivity {
    private List<BaseItem> items = new ArrayList<>();
    ItemAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        registerViews();

    }
    private void registerViews(){
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        adapter = new ItemAdapter(getBaseContext(), items);
        recyclerView.setAdapter(adapter);

    }
}
