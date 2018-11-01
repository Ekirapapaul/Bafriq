package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mesozi.app.buidafrique.R;

/**
 * Created by ekirapa on 10/29/18 .
 */
public class RedeemBonus extends AppCompatActivity {
    private final int MPESA = 1;
    private final int BANK = 2;
    private final int PAYPAL = 3;
    CardView cardPaypal, cardMpesa, cardBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_bonus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardPaypal = findViewById(R.id.card_paypal);
        cardMpesa = findViewById(R.id.card_mpesa);
        cardBank = findViewById(R.id.card_bank);

        cardBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(BANK);
            }
        });

        cardPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(PAYPAL);
            }
        });

        cardMpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(MPESA);
            }
        });
    }

    private void change(int number) {
        if (number == MPESA) {
            findViewById(R.id.linear_mpesa).setVisibility(View.VISIBLE);
            findViewById(R.id.linear_bank).setVisibility(View.GONE);
            findViewById(R.id.linear_paypal).setVisibility(View.GONE);
        } else if (number == BANK) {
            findViewById(R.id.linear_mpesa).setVisibility(View.GONE);
            findViewById(R.id.linear_bank).setVisibility(View.VISIBLE);
            findViewById(R.id.linear_paypal).setVisibility(View.GONE);

        } else if (number == PAYPAL) {
            findViewById(R.id.linear_mpesa).setVisibility(View.GONE);
            findViewById(R.id.linear_bank).setVisibility(View.GONE);
            findViewById(R.id.linear_paypal).setVisibility(View.VISIBLE);
        }
    }
}
