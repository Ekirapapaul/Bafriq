package com.buildafrique.pn.app.activity.Resources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.buildafrique.pn.app.Models.ChildResource;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.RecyclerItemClickListener;
import com.buildafrique.pn.app.adapters.ChildResourceAdapter;
import com.buildafrique.pn.app.adapters.ResourceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/16/18 .
 */
public class TrainingEvents extends AppCompatActivity {

    private ChildResourceAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsources);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("Training Eents");
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        setResources();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Intent vidIntent = new Intent(Intent.ACTION_VIEW);
                        vidIntent.setData(Uri.parse("https://buildafrique.com/resources/e-publications/"));
                        startActivity(vidIntent);
                        break;
                }
            }
        }));

    }

    private void setResources() {
        List<ChildResource> resources = new ArrayList<>();
        resources.add(new ChildResource("Training Eents", R.drawable.ic_event_black_24dp));

        adapter = new ChildResourceAdapter(getBaseContext(), resources);

    }
}
