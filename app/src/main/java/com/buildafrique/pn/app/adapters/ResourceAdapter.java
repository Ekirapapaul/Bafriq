package com.buildafrique.pn.app.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildafrique.pn.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 9/13/18 .
 */
public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceHolder> {
    Context context;
    private int[] ids = {R.drawable.ic_help_black_24dp, R.drawable.ic_view_list_black_24dp, R.drawable.ic_folder_open_black_24dp, R.drawable.ic_announcement_black_24dp, R.drawable.ic_event_black_24dp, R.drawable.ic_web_black_24dp, R.drawable.ic_info_black_24dp};
    List<String> resources = new ArrayList<>();

    public ResourceAdapter(Context context, List<String> resources) {
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
        holder.textView.setText(resources.get(position));
        Drawable img = context.getResources().getDrawable(ids[position]);
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
