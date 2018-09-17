package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.mesozi.app.buidafrique.Fragments.ProductsFrag;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.adapters.ViewPagerAdapter;

import java.util.Objects;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class ProductsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        registerVViews();
    }
    private void registerVViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ProductsFrag frag1 = new ProductsFrag();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",1);
        frag1.setArguments(bundle);
        adapter.addFragment(frag1, "FINANCE & MANAGEMENT");

        ProductsFrag frag2 = new ProductsFrag();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("tag",2);
        frag2.setArguments(bundle2);
        adapter.addFragment(frag2, "QUANTITY SURVEYING");

        ProductsFrag frag3 = new ProductsFrag();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("tag",3);
        frag3.setArguments(bundle3);
        adapter.addFragment(frag3, "LAND USE & ENVIRONMENT");

        ProductsFrag frag4 = new ProductsFrag();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("tag",4);
        frag4.setArguments(bundle4);
        adapter.addFragment(frag4, "INVESTMENTS & PROPERTY");
        viewPager.setAdapter(adapter);
    }
}
