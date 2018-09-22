package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Customer;
import com.mesozi.app.buidafrique.Models.Customer_Table;
import com.mesozi.app.buidafrique.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/9/18 .
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ItemViewHolder> {
    private Context context;
    private List<Customer> customers = new ArrayList<>();

    public CustomerAdapter(Context context, List<Customer> customers) {
        this.context = context;
        this.customers = SQLite.select()
                .from(Customer.class)
                .where(Customer_Table.email.notEq("false"))
                .queryList();;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_customer_item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Customer customer = customers.get(position);
        if (position == (customers.size() - 1)) holder.separator.setVisibility(View.GONE);
        holder.name.setText(customer.getName());
        holder.contact.setText(customer.getEmail());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View separator;
        TextView name, contact, mobile;

        ItemViewHolder(View itemView) {
            super(itemView);
            separator = itemView.findViewById(R.id.separator);
            name = itemView.findViewById(R.id.tv_name);
            contact = itemView.findViewById(R.id.tv_contact);
            mobile = itemView.findViewById(R.id.tv_mobile);
        }
    }
}