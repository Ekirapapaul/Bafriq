package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.AbstractSalesOrder;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.adapters.OrderLineAdapater;

/**
 * Created by ekirapa on 7/26/18 .
 */
public class SalesOrderDetails extends AppCompatActivity {
    AbstractSalesOrder salesOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_item);
        registerViews();
    }

    private void registerViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        TextView salesNo = findViewById(R.id.tv_sales_no);
        TextView reference = findViewById(R.id.tv_reference);
        TextView state = findViewById(R.id.tv_state);
        TextView date = findViewById(R.id.tv_date);
        TextView amount = findViewById(R.id.tv_amount);



        salesOrder = getIntent().getParcelableExtra("parcel_data");
        if (salesOrder != null) {
            salesNo.setText(salesOrder.getName());
            reference.setText(((salesOrder.getClient_order_ref().isEmpty()) ? "N/A" : salesOrder.getClient_order_ref()));
            state.setText(salesOrder.getState());
            date.setText(salesOrder.getDate());
            amount.setText(String.format("Total Amount : %s", salesOrder.getAmount_total()));

            RecyclerView recyclerView = findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView.setAdapter(new OrderLineAdapater(getBaseContext(), salesOrder.getOrder_line()));
        }

    }
}
