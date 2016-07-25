package com.techHome.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.neopixl.pixlui.components.textview.TextView;
import com.squareup.picasso.Picasso;
import com.techHome.R;
import com.techHome.activities.AppliancesActivity;
import com.techHome.activities.DashboardActivity;
import com.techHome.activities.PlumbingActivity;
import com.techHome.activities.WiringActivity;
import com.techHome.constants.DashboardRecyclerInformation;
import com.techHome.util.ImageLoader;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dell on 4/29/2016.
 */

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder> {

    private LayoutInflater inflater;
    List<DashboardRecyclerInformation> data = Collections.emptyList();
    private Context context;

    public DashboardRecyclerAdapter(Context context, List<DashboardRecyclerInformation> data) {
        this.context = context;
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
        holder.txtTitleDescription.setText(current.desc);
        Picasso picasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
        picasso.load(data.get(position).getIconId()).placeholder(R.mipmap.ic_time_black).fit().centerInside().into(holder.imgRecyclerIcon);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtRecyclerTitle;
        ImageView imgRecyclerIcon;
        TextView txtTitleDescription;

        public DashboardViewHolder(View itemView) {
            super(itemView);

            txtRecyclerTitle = (TextView) itemView.findViewById(R.id.txtRecyclerTitle);
            imgRecyclerIcon = (ImageView) itemView.findViewById(R.id.imgRecyclerIcon);
            txtTitleDescription = (TextView) itemView.findViewById(R.id.txtTitleDescription);
            imgRecyclerIcon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos = getPosition();

            switch (pos){
                case 0:
                    context.startActivity(new Intent(context, AppliancesActivity.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context, WiringActivity.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context, PlumbingActivity.class));
                    break;
            }

        }
    }
}
