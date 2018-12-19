package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Customer;
import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.Lead_Table;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.CommonUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.LeadsHolder>  implements Filterable {
    private Context context;
    private List<Lead> leads = new ArrayList<>();
    private List<Lead> filteredLeads = new ArrayList<>();

    public LeadsAdapter(Context context, List<Lead> leads) {
        this.context = context;
        this.leads = leads;
        this.filteredLeads = leads;
    }

    @NonNull
    @Override
    public LeadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_leads, parent, false);
        return new LeadsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsHolder holder, int position) {
        Lead lead = filteredLeads.get(position);
        holder.title.setText(lead.getName());
        holder.summary.setText(lead.getDescription());

            String stage = lead.getStage_id();
            holder.state.setText(lead.getStage_id());
//            if (stage.equals("WON Opportunity")){
//                holder.state.setText(R.string.won);
//                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_green_24dp, 0, 0, 0);
//            }else if(stage.equals("Negotiation")){
//                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_red_24dp, 0, 0, 0);
//            }else if(stage.equals("Qualification")) {
//                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_blue_24dp, 0, 0, 0);
//            }else if(stage.equals("New")) {
//                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_purple_24dp, 0, 0, 0);
//            }else{
//                holder.state.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_checked_black_24dp, 0, 0, 0);
//            }
            if (stage.equals("WON Opportunity")){
                holder.state.setText(R.string.won);
                holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_green));
            }else if(stage.equals("LOST Opportunity")){
                holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_red));
            }else if(stage.equals("Quotation (Proposition)")) {
                holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_blue));
            }else if(stage.equals("Negotiation")) {
                holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_purple));
            }else{
                holder.state.setBackgroundColor(ContextCompat.getColor(context, R.color.menu_orange));
            }



        try {
            Date date = CommonUtils.parseStringToDate(lead.getCreate_date());
            String month = (String) DateFormat.format("EEE", date);
            String day = (String) DateFormat.format("dd", date);
            String year = (String) DateFormat.format("yyyy", date);
            holder.day.setText(day);
            holder.month.setText(month);
            holder.year.setText(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return filteredLeads.size();
    }

    public Lead getLead(int position){
        return filteredLeads.get(position);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredLeads = leads;
                } else {
                    List<Lead> filteredList = new ArrayList<>();
                    for (Lead lead : leads) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (lead.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(lead);
                            Log.d("Filtered", charString + " gotten " + lead.getName());
                        }
                    }

                    filteredLeads = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredLeads;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredLeads = (ArrayList<Lead>) filterResults.values;notifyDataSetChanged();
            }
        };
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
