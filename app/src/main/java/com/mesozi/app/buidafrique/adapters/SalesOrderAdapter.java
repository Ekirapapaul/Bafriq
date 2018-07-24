package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.SalesOrder;
import com.mesozi.app.buidafrique.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class SalesOrderAdapter extends RecyclerView.Adapter<SalesOrderAdapter.LeadsHolder> {
    private Context context;
    private List<SalesOrder> salesOrders = new ArrayList<>();

    public SalesOrderAdapter(Context context, List<SalesOrder> salesOrders) {
        this.context = context;
        this.salesOrders = salesOrders;
    }

    @NonNull
    @Override
    public LeadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_leads, parent, false);
        return new LeadsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsHolder holder, int position) {
        SalesOrder salesOrder = salesOrders.get(position);
        holder.title.setText(salesOrder.getDisplay_name());
        holder.summary.setText(salesOrder.getClient_order_ref());

    }

    @Override
    public int getItemCount() {
        return salesOrders.size();
    }

    class LeadsHolder extends RecyclerView.ViewHolder {
        TextView month, year, day, title, summary;

        public LeadsHolder(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.tv_date_month);
            day = itemView.findViewById(R.id.tv_date_date);
            year = itemView.findViewById(R.id.tv_date_year);
            title = itemView.findViewById(R.id.tv_lead_title);
            summary = itemView.findViewById(R.id.tv_lead_summary);
        }
    }
}
