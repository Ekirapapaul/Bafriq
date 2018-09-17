package com.mesozi.app.buidafrique.activity.Resources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.adapters.ResourceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/16/18 .
 */
public class HowItWorks extends AppCompatActivity {

    private ResourceAdapter adapter;

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
        toolbar.setTitle("How It Works");
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
                        vidIntent.setData(Uri.parse("https://buildafrique.com/video/kenyas-real-estate-investments-our-perspective-approach-model/"));
                        startActivity(vidIntent);
                        break;
                    case 1:
                        Intent flyerIntent = new Intent(Intent.ACTION_VIEW);
                        flyerIntent.setData(Uri.parse("https://buildafrique.com/wp-content/uploads/2018/09/Buildafrique-Partner-Network-POSTER.jpg"));
                        startActivity(flyerIntent);
                        break;
                    case 2:
                        Intent siteIntent = new Intent(Intent.ACTION_VIEW);
                        siteIntent.setData(Uri.parse("https://partner-network.buildafrique.com/how-it-works/"));
                        startActivity(siteIntent);
                        break;
                }
            }
        }));

    }

    private void setResources() {
        List<String> resources = new ArrayList<>();
        resources.add("Videos");
        resources.add("Flyer");
        resources.add("Website");

        adapter = new ResourceAdapter(getBaseContext(), resources);

    }
}
