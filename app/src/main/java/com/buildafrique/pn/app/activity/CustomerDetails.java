package com.buildafrique.pn.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.buildafrique.pn.app.Models.Customer;
import com.buildafrique.pn.app.R;

/**
 * Created by ekirapa on 9/4/18 .
 */
public class CustomerDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Customer customer = getIntent().getParcelableExtra("parcel_data");

        TextView name = findViewById(R.id.tv_name);
        TextView mail = findViewById(R.id.tv_mail);
        TextView mobile = findViewById(R.id.tv_mobile);
        TextView phone = findViewById(R.id.tv_phone);

        if (customer != null) {
            name.setText(customer.getName());
            mail.setText(customer.getEmail());
            mobile.setText(!customer.getMobile().equals("false") ? customer.getMobile() : "N/A");
            phone.setText((customer.getPhone() != null && !customer.getPhone().equals("false")) ? customer.getPhone() : "N/A");
        }

        findViewById(R.id.btn_add_lead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddLead.class);
                intent.putExtra("parcel_data", customer);
                startActivity(intent);
            }
        });
    }
}
