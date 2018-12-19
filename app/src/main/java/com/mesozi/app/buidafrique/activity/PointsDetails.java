package com.mesozi.app.buidafrique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mesozi.app.buidafrique.Models.Bonus;
import com.mesozi.app.buidafrique.Models.Loyalty;
import com.mesozi.app.buidafrique.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Locale;

/**
 * Created by ekirapa on 8/23/18 .
 */
public class PointsDetails extends AppCompatActivity {
    TextView total, available;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setViews();
    }

    private void setViews() {
        total = findViewById(R.id.tv_total);
        available = findViewById(R.id.tv_available);

        final Loyalty loyalty = SQLite.select().from(Loyalty.class).querySingle();
        if (loyalty != null) {
            available.setText(String.format(Locale.getDefault(), "%d", loyalty.getAvailable()));
            total.setText(String.format(Locale.getDefault(), "%d", loyalty.getRedeemed()));
        }

        findViewById(R.id.btn_redeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loyalty != null) {
                    if (loyalty.getAvailable() > 0) {
                        Intent intent = new Intent(getBaseContext(), RedeemLoyalty.class);
                        intent.putExtra("parcel_data", loyalty);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PointsDetails.this, "You have no available points", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
