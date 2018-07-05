package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.mesozi.app.buidafrique.R;

/**
 * Created by ekirapa on 7/4/18 .
 */
public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private TextView placeholder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        registerViews();
    }

    private void registerViews(){
        placeholder = findViewById(R.id.tv_search_placeholder);
        searchView = findViewById(R.id.search_view);
        //searchView.onActionViewExpanded();
        searchView.setFocusable(true);// searchView is null
        searchView.setFocusableInTouchMode(true);

        placeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeholder.setVisibility(View.GONE);
                searchView.onActionViewExpanded();
            }
        });

    }
}
