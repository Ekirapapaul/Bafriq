package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.RefferalOption;
import com.mesozi.app.buidafrique.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 12/6/18 .
 */
public class RefferalAdapter extends RecyclerView.Adapter<RefferalAdapter.RefferalHolder> {
    private Context context;
    List<RefferalOption> refferalOptions = new ArrayList<>();

    public RefferalAdapter(Context context) {
        this.context = context;
        refferalOptions = SQLite.select().from(RefferalOption.class).queryList();
    }

    @NonNull
    @Override
    public RefferalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_refferal, parent, false);
        return new RefferalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefferalHolder holder, int position) {
        RefferalOption option = refferalOptions.get(position);
        holder.amount.setText(String.valueOf(option.getLoyalty_amount()));
        holder.name.setText(option.getName());
    }

    public RefferalOption getOPtion(int position){
        return refferalOptions.get(position);
    }
    @Override
    public int getItemCount() {
        return refferalOptions.size();
    }

    class RefferalHolder extends RecyclerView.ViewHolder {
        TextView amount, name;

        public RefferalHolder(View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.tv_amount);
            name = itemView.findViewById(R.id.tv_option);
        }
    }
}
