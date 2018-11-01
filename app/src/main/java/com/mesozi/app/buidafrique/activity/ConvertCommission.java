package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mesozi.app.buidafrique.R;

/**
 * Created by ekirapa on 10/29/18 .
 */
public class ConvertCommission extends AppCompatActivity {
    private EditText etAmount;
    private TextView tvPoints;
    private final int CONVERSION_RATE = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etAmount = findViewById(R.id.et_amount);
        tvPoints = findViewById(R.id.tv_points);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    try {
                        int amount = Integer.parseInt(String.valueOf(charSequence));
                        int points = amount / CONVERSION_RATE;
                        tvPoints.setText(String.valueOf(points));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
