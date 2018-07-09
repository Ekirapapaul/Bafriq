package com.mesozi.app.buidafrique.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesozi.app.buidafrique.Models.BaseItem;
import com.mesozi.app.buidafrique.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/9/18 .
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context context;
    private List<BaseItem> items = new ArrayList<>();

    public ItemAdapter(Context context, List<BaseItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_base_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (position == (items.size() - 1)) holder.separator.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View separator;

        public ItemViewHolder(View itemView) {
            super(itemView);
            separator = itemView.findViewById(R.id.separator);
        }
    }
}
