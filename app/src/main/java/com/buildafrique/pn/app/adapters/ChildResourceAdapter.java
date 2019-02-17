package com.buildafrique.pn.app.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildafrique.pn.app.Models.ChildResource;
import com.buildafrique.pn.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/13/18 .
 */
public class ChildResourceAdapter extends RecyclerView.Adapter<ChildResourceAdapter.ResourceHolder> {
    Context context;
    List<ChildResource> resources = new ArrayList<>();

    public ChildResourceAdapter(Context context, List<ChildResource> resources) {
        this.context = context;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ResourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_resource, parent, false);
        return new ResourceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceHolder holder, int position) {
        holder.textView.setText(resources.get(position).getTitle());
        Drawable img = context.getResources().getDrawable(resources.get(position).getIcon());
        img.setBounds(0, 0, 60, 60);
        holder.textView.setCompoundDrawables(img, null, null, null);
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    class ResourceHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ResourceHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_resource_name);
        }
    }
}
