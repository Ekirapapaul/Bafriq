package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.SalesOrder;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.CommonUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_sales_orders, parent, false);
        return new LeadsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsHolder holder, int position) {
        SalesOrder salesOrder = salesOrders.get(position);
        try {
            Date date = CommonUtils.parseStringToDate(salesOrder.getCreate_date());
            String month = (String) DateFormat.format("EEE", date);
            String day = (String) DateFormat.format("dd", date);
            String year = (String) DateFormat.format("yyyy", date);
            holder.day.setText(day);
            holder.month.setText(month);
            holder.year.setText(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.title.setText(salesOrder.getDisplay_name());
        holder.summary.setText(salesOrder.getClient_order_ref());
        if (salesOrder.getCurrency_id().length >= 2) {
            holder.currency.setText(String.format("%s %s", salesOrder.getCurrency_id()[1], salesOrder.getCurrency_id()[0]));
        }
        if (!salesOrder.getState().equals("false")) {
            String state = salesOrder.getState().substring(0, 1).toUpperCase() + salesOrder.getState().substring(1);
            if (state.equals("Done")){
                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_green_24dp, 0, 0, 0);
            }else if(state.equals("Manual")){
                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_red_24dp, 0, 0, 0);
            }else if(state.equals("Draft")) {
                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_blue_24dp, 0, 0, 0);
            }else if(state.equals("Sent")) {
                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_purple_24dp, 0, 0, 0);
            }else{
                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_black_24dp, 0, 0, 0);
            }
                holder.state.setText(state);
        }

    }


    @Override
    public int getItemCount() {
        return salesOrders.size();
    }

    class LeadsHolder extends RecyclerView.ViewHolder {
        TextView month, year, day, title, summary, currency, state;

        public LeadsHolder(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.tv_date_month);
            day = itemView.findViewById(R.id.tv_date_date);
            year = itemView.findViewById(R.id.tv_date_year);
            title = itemView.findViewById(R.id.tv_lead_title);
            summary = itemView.findViewById(R.id.tv_lead_summary);
            currency = itemView.findViewById(R.id.tv_currency);
            state = itemView.findViewById(R.id.tv_state);
        }
    }

}
