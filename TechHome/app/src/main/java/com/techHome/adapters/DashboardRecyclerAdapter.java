package com.techHome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.constants.DashboardRecyclerInformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dell on 4/29/2016.
 */

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder> {

    private LayoutInflater inflater;
    List<DashboardRecyclerInformation> data = Collections.emptyList();

    public DashboardRecyclerAdapter(Context context, List<DashboardRecyclerInformation> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dashboard_recycler_custom_row, parent, false);
        DashboardViewHolder holder = new DashboardViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DashboardViewHolder holder, int position) {

        DashboardRecyclerInformation current = data.get(position);
        holder.txtRecyclerTitle.setText(current.title);
        holder.imgRecyclerIcon.setImageResource(current.iconId);
        holder.txtTitleDescription.setText(current.desc);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder {

        TextView txtRecyclerTitle;
        ImageView imgRecyclerIcon;
        TextView txtTitleDescription;

        public DashboardViewHolder(View itemView) {
            super(itemView);

            txtRecyclerTitle = (TextView) itemView.findViewById(R.id.txtRecyclerTitle);
            imgRecyclerIcon = (ImageView) itemView.findViewById(R.id.imgRecyclerIcon);
            txtTitleDescription = (TextView) itemView.findViewById(R.id.txtTitleDescription);

        }
    }
}
