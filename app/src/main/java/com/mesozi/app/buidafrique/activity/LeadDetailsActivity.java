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
import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.adapters.OrderLineAdapater;

/**
 * Created by ekirapa on 7/26/18 .
 */
public class LeadDetailsActivity extends AppCompatActivity {
    Lead lead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads_item);
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

        TextView name = findViewById(R.id.tv_lead_name);
        TextView description = findViewById(R.id.tv_description);
        TextView state = findViewById(R.id.tv_state);
        TextView date = findViewById(R.id.tv_date);
        TextView review = findViewById(R.id.tv_review);
        TextView partnerName = findViewById(R.id.tv_partner_name);
        TextView contactName = findViewById(R.id.tv_contact_name);
        TextView mobile = findViewById(R.id.tv_mobile);



        lead = getIntent().getParcelableExtra("parcel_data");
        if (lead != null) {
            name.setText(lead.getName());
            description.setText(((lead.getDescription().isEmpty()) ? "N/A" : lead.getDescription()));
            String stage = "N/A";
            if (lead.getStage_id().length >=2) {
                stage = lead.getStage_id()[1].substring(0, 1).toUpperCase() + lead.getStage_id()[1].substring(1);
            }
            state.setText(stage);
            date.setText(lead.getCreate_date());
            review.setText(lead.getPlanned_revenue());

            partnerName.setText(lead.getContact_name());
            contactName.setText(lead.getContact_name());
            mobile.setText(lead.getMobile());
        }

    }
}
