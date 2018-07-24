package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.LeadsHolder> {
    private Context context;
    private List<Lead> leads = new ArrayList<>();

    public LeadsAdapter(Context context, List<Lead> leads) {
        this.context = context;
        this.leads = leads;
    }

    @NonNull
    @Override
    public LeadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_leads, parent, false);
        return new LeadsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsHolder holder, int position) {
        Lead lead = leads.get(position);
        holder.title.setText(lead.getName());
        holder.summary.setText(lead.getDescription());

    }

    @Override
    public int getItemCount() {
        return leads.size();
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
