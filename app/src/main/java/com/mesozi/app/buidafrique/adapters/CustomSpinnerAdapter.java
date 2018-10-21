package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 10/21/18 .
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    List<Customer> customers = new ArrayList<>();

    public CustomSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CustomSpinnerAdapter(@NonNull Context context, int resource, List<Customer> customers) {
        super(context, resource);
        this.customers = customers;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public String getItem(int position) {
        return customers.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(customers.get(position).getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(customers.get(position).getName());

        return label;
    }
}
