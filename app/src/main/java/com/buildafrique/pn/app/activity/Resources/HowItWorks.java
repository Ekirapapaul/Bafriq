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
public class HowItWorks extends AppCompatActivity {

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
                        vidIntent.setData(Uri.parse("https://youtu.be/v_aLXWxUcAw"));
                        startActivity(vidIntent);
                        break;
                    case 1:
                        Intent flyerIntent = new Intent(Intent.ACTION_VIEW);
                        flyerIntent.setData(Uri.parse("https://buildafrique.com/wp-content/uploads/2019/01/Website-Flyer-HOW-IT-WORK-03-01-19.pdf"));
                        startActivity(flyerIntent);
                        break;
                    case 2:
//                        Intent siteIntent = new Intent(Intent.ACTION_VIEW);
//                        siteIntent.setData(Uri.parse("https://partner-network.buildafrique.com/how-it-works/"));
//                        startActivity(siteIntent);
                        break;
                }
            }
        }));

    }

    private void setResources() {
        List<ChildResource> resources = new ArrayList<>();
        resources.add(new ChildResource("Videos",R.drawable.ic_videocam_black_24dp));
        resources.add(new ChildResource("Flyer", R.drawable.ic_import_contacts_black_24dp));
//        resources.add(new ChildResource("Website", R.drawable.ic_web_black_24dp));

        adapter = new ChildResourceAdapter(getBaseContext(), resources);

    }
}
