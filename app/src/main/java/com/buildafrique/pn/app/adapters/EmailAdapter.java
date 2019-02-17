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

import com.buildafrique.pn.app.Models.Customer;
import com.buildafrique.pn.app.Models.EmailMessage;
import com.buildafrique.pn.app.Models.EmailMessage_Table;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.CommonUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/7/18 .
 */
public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailHolder> implements Filterable {

    private Context context;
    private List<EmailMessage> emails = new ArrayList<>();
    private List<EmailMessage> filteredEmail = new ArrayList<>();

    public EmailAdapter(Context context, List<EmailMessage> emails) {
        this.context = context;
        this.emails = SQLite.select().from(EmailMessage.class).where(EmailMessage_Table.to_read.eq(true)).queryList();
        this.filteredEmail = emails;
    }

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_inbox_message, parent, false);
        return new EmailHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailHolder holder, int position) {
        EmailMessage emailMessage = filteredEmail.get(position);
        holder.date.setText(emailMessage.getDate());

        holder.subject.setText((emailMessage.getSubject().equals("false") ? "No Subject" : emailMessage.getSubject()));
        if (emailMessage.getEmail_from().length() > 0) {
            char letter = emailMessage.getEmail_from().toUpperCase().charAt(0);
            holder.initials.setText(String.valueOf(letter));
        }
        holder.name.setText(emailMessage.getEmail_from());
        holder.exerpt.setText(CommonUtils.fromHtml(emailMessage.getBody()));
        //holder.initials.setText(emailMessage.getEmail_from().charAt(0));

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredEmail = emails;
                } else {
                    List<EmailMessage> filteredList = new ArrayList<>();
                    for (EmailMessage emailMessage : emails) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (emailMessage.getSubject().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(emailMessage);
                            Log.d("Filtered", charString + " gotten " + emailMessage.getSubject());
                        }
                    }

                    filteredEmail = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredEmail;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredEmail = (ArrayList<EmailMessage>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public EmailMessage getEmail(int position) {
        return filteredEmail.get(position);
    }

    @Override
    public int getItemCount() {
        return filteredEmail.size();
    }

    class EmailHolder extends RecyclerView.ViewHolder {
        TextView initials, subject, name, exerpt, date;

        public EmailHolder(View itemView) {
            super(itemView);
            initials = itemView.findViewById(R.id.tv_initials);
            subject = itemView.findViewById(R.id.tv_subject);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            exerpt = itemView.findViewById(R.id.tv_exerpt);
        }
    }
}
