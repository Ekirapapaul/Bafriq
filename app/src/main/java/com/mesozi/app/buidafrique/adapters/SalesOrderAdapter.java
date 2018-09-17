package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.AbstractSalesOrder;
import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.OrderLine;
import com.mesozi.app.buidafrique.Models.SalesOrder;
import com.mesozi.app.buidafrique.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class SalesOrderAdapter extends RecyclerView.Adapter<SalesOrderAdapter.LeadsHolder> {
    private Context context;
    private List<AbstractSalesOrder> salesOrders = new ArrayList<>();

    public SalesOrderAdapter(Context context, List<AbstractSalesOrder> salesOrders) {
        this.context = context;
        this.salesOrders = salesOrders;
    }

    @NonNull
    @Override
    public LeadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_sales_orders, parent, false);
        return new LeadsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsHolder holder, int position) {
        AbstractSalesOrder salesOrder = salesOrders.get(position);
        holder.title.setText(salesOrder.getName());

        Log.d("object",salesOrder.getOrder_line().toString());

        try {
            OrderLine orderLine = salesOrder.getOrder_line().get(0);
            holder.summary.setText(orderLine.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String stage = salesOrder.getState().substring(0, 1).toUpperCase() + salesOrder.getState().substring(1);
        holder.state.setText(stage);
//        if (stage.equals("Manual")) {
//            holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_green_24dp, 0, 0, 0);
//        } else if (stage.equals("Negotiation")) {
//            holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_red_24dp, 0, 0, 0);
//        } else if (stage.equals("Qualification")) {
//            holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_blue_24dp, 0, 0, 0);
//        } else if (stage.equals("New")) {
//            holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_purple_24dp, 0, 0, 0);
//        } else {
//            holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_black_24dp, 0, 0, 0);
//        }

        if (stage.equals("Manual")) {
            holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_green));
        } else if (stage.equals("Negotiation")) {
            holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_red));
        } else if (stage.equals("Qualification")) {
            holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_blue));
        } else if (stage.equals("New")) {
            holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_purple));
        } else {
            holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_orange));
        }

    }

    @Override
    public int getItemCount() {
        return salesOrders.size();
    }

    class LeadsHolder extends RecyclerView.ViewHolder {
        TextView month, year, day, title, summary, state;

        public LeadsHolder(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.tv_date_month);
            day = itemView.findViewById(R.id.tv_date_date);
            year = itemView.findViewById(R.id.tv_date_year);
            title = itemView.findViewById(R.id.tv_lead_title);
            summary = itemView.findViewById(R.id.tv_lead_summary);
            state = itemView.findViewById(R.id.tv_state);
        }
    }
}
