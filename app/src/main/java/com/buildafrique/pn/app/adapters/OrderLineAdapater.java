package com.buildafrique.pn.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildafrique.pn.app.Models.OrderLine;
import com.buildafrique.pn.app.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by ekirapa on 7/26/18 .
 */
public class OrderLineAdapater extends RecyclerView.Adapter<OrderLineAdapater.OrderLineHolder> {
    private Context context;
    private List<OrderLine> orderLines;

    public OrderLineAdapater(Context context, List<OrderLine> orderLines) {
        this.context = context;
        this.orderLines = orderLines;
    }

    @NonNull
    @Override
    public OrderLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_line, parent, false);
        return new OrderLineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderLineHolder holder, int position) {
        OrderLine orderLine = orderLines.get(position);
        holder.sub_total.setText(String.format(Locale.getDefault(), "Sub total : %d", orderLine.getPrice_subtotal()));
        holder.unit.setText(String.format(Locale.getDefault(), "Unit price : %d", orderLine.getPrice_unit()));
        holder.quantity.setText(String.format(Locale.getDefault(), "Quantity  : %d", orderLine.getProduct_uom_qty()));
        holder.name.setText(String.format("Name : %s", orderLine.getName()));
    }

    @Override
    public int getItemCount() {
        return orderLines.size();
    }

    class OrderLineHolder extends RecyclerView.ViewHolder {
        TextView name, unit, quantity, sub_total;

        public OrderLineHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            unit = itemView.findViewById(R.id.tv_unit_price);
            quantity = itemView.findViewById(R.id.tv_quantity);
            sub_total = itemView.findViewById(R.id.tv_subtotal);
        }
    }
}
