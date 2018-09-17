package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.EmailMessage;
import com.mesozi.app.buidafrique.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/7/18 .
 */
public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailHolder> {

    private Context context;
    private List<EmailMessage> emails = new ArrayList<>();

    public EmailAdapter(Context context, List<EmailMessage> emails) {
        this.context = context;
        this.emails = emails;
    }

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_inbox_message, parent, false);
        return new EmailHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailHolder holder, int position) {
        EmailMessage emailMessage = emails.get(position);
        holder.date.setText(emailMessage.getDate());
        holder.subject.setText((emailMessage.getSubject().equals("false") ? "N/A" : emailMessage.getSubject()));
        holder.name.setText(emailMessage.getName());
        //holder.initials.setText(emailMessage.getEmail_from().charAt(0));

    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    class EmailHolder extends RecyclerView.ViewHolder {
        TextView initials, subject, name, date;

        public EmailHolder(View itemView) {
            super(itemView);
            initials = itemView.findViewById(R.id.tv_initials);
            subject = itemView.findViewById(R.id.tv_subject);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
}
