package com.buildafrique.pn.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.buildafrique.pn.app.Models.Customer;
import com.buildafrique.pn.app.Models.Customer_Table;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.activity.CustomerActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/9/18 .
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ItemViewHolder> implements Filterable {
    private Context context;
    private List<Customer> customers = new ArrayList<>();
    private List<Customer> customersListFiltered = new ArrayList<>();
    private CustomerAdapterListener listener;

    public CustomerAdapter(Context context, List<Customer> customers) {
        this.context = context;
        this.customers = SQLite.select()
                .from(Customer.class)
                .queryList();
        this.customersListFiltered = customers;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_customer_item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Customer customer = customersListFiltered.get(position);
        if (position == (customersListFiltered.size() - 1)) holder.separator.setVisibility(View.GONE);
        holder.name.setText(customer.getName());
        holder.contact.setText(customer.getEmail());
    }

    @Override
    public int getItemCount() {
        return customersListFiltered.size();
    }

    public Customer getCustomer(int position){
        return customersListFiltered.get(position);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customersListFiltered = customers;
                } else {
                    List<Customer> filteredList = new ArrayList<>();
                    for (Customer customer : customers) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (customer.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(customer);
                            Log.d("Filtered", charString + " gotten " + customer.getName());
                        }
                    }

                    customersListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customersListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                customersListFiltered = (ArrayList<Customer>) filterResults.values;notifyDataSetChanged();
            }
        };
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(customersListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface CustomerAdapterListener {
        void onContactSelected(Customer customer);
    }
}
