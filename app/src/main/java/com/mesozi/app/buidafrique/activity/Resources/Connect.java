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

import com.mesozi.app.buidafrique.Models.ChildResource;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.adapters.ChildResourceAdapter;
import com.mesozi.app.buidafrique.adapters.ResourceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/16/18 .
 */
public class Connect extends AppCompatActivity {

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
        toolbar.setTitle("Connect With Us");
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
                        vidIntent.setData(Uri.parse("https://partner-network.buildafrique.com/"));
                        startActivity(vidIntent);
                        break;
                    case 1:
                        Intent fbIntent = new Intent(Intent.ACTION_VIEW);
                        fbIntent.setData(Uri.parse("https://web.facebook.com/BuildafriquePartnerNetwork/?_rdc=1&_rdr"));
                        startActivity(fbIntent);
                        break;
                    case 2:
                        Intent twitterIntent = new Intent(Intent.ACTION_VIEW);
                        twitterIntent.setData(Uri.parse("https://twitter.com/buildafriquePN"));
                        startActivity(twitterIntent);
                        break;
                    case 3:
                        Intent linIntent = new Intent(Intent.ACTION_VIEW);
                        linIntent.setData(Uri.parse("https://www.linkedin.com/company/partner-network.buildafrique.com/"));
                        startActivity(linIntent);
                        break;
                    case 4:
                        Intent instIntent = new Intent(Intent.ACTION_VIEW);
                        instIntent.setData(Uri.parse("https://www.instagram.com/buildafrique_partner_network/"));
                        startActivity(instIntent);
                        break;

                }
            }
        }));

    }

    private void setResources() {
        List<ChildResource> resources = new ArrayList<>();
        resources.add(new ChildResource("Website", R.drawable.ic_public_black_24dp));
        resources.add(new ChildResource("Facebook", R.drawable.facebook));
        resources.add(new ChildResource("Twitter", R.drawable.twitter));
        resources.add(new ChildResource("LinkedIn", R.drawable.linkedin_box));
        resources.add(new ChildResource("Instagram", R.drawable.instagram));

        adapter = new ChildResourceAdapter(getBaseContext(), resources);

    }
}
