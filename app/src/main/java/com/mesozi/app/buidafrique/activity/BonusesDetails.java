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
import com.mesozi.app.buidafrique.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Locale;

/**
 * Created by ekirapa on 8/23/18 .
 */
public class BonusesDetails extends AppCompatActivity {
    TextView total, expected, available;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonuses);
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
        expected = findViewById(R.id.tv_expected);
        available = findViewById(R.id.tv_available);

        final Bonus bonus = SQLite.select().from(Bonus.class).querySingle();
        if (bonus != null) {
            available.setText(String.format(Locale.getDefault(), "KES %d", bonus.getAvailable()));
            expected.setText(String.format(Locale.getDefault(), "KES %d", bonus.getExpected()));
            total.setText(String.format(Locale.getDefault(), "KES %d", bonus.getPaid()));
        }
        findViewById(R.id.btn_redeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bonus != null) {
                    if (bonus.getAvailable() > 0) {
                        Intent intent = new Intent(getBaseContext(), RedeemBonus.class);
                        intent.putExtra("parcel_data", bonus);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BonusesDetails.this, "You have no available bonus points", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
