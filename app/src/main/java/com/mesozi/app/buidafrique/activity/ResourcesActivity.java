package com.mesozi.app.buidafrique.activity;

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
import com.mesozi.app.buidafrique.activity.Resources.About;
import com.mesozi.app.buidafrique.activity.Resources.Connect;
import com.mesozi.app.buidafrique.activity.Resources.HowItWorks;
import com.mesozi.app.buidafrique.activity.Resources.MarketingMaterials;
import com.mesozi.app.buidafrique.adapters.ResourceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/13/18 .
 */
public class ResourcesActivity extends AppCompatActivity {
    ResourceAdapter adapter;

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

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        setResources();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getBaseContext(), HowItWorks.class));
                        break;
                    case 1:
                        startActivity(new Intent(getBaseContext(), ProductsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getBaseContext(), MarketingMaterials.class));
                        break;
                    case 3:
                        Intent vidIntent = new Intent(Intent.ACTION_VIEW);
                        vidIntent.setData(Uri.parse("https://partner-network.buildafrique.com/newsletters/"));
                        startActivity(vidIntent);
                        break;
                    case 4:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://partner-network.buildafrique.com/events/"));
                        startActivity(intent);
                        break;
                    case 5:
                        startActivity(new Intent(getBaseContext(), Connect.class));
                        break;
                    case 6:
                        startActivity(new Intent(getBaseContext(), About.class));
                        break;
                    case 7:
                        break;

                }
            }
        }));

    }

    private void setResources() {
        List<String> resources = new ArrayList<>();
        resources.add("How it Works");
        resources.add("Products");
        resources.add("Marketing Materials");
        resources.add("Monthly Newsletters");
        resources.add("Training Events");
        resources.add("Connect With Us");
        resources.add("About The Company");

        adapter = new ResourceAdapter(getBaseContext(), resources);

    }
}
