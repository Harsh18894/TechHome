package com.techHome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.constants.AboutUsRecyclerInformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Harsh on 5/16/2016.
 */
public class AboutUsRecyclerAdapter extends RecyclerView.Adapter<AboutUsRecyclerAdapter.AboutUsViewHolder> {


    private LayoutInflater inflater;
    List<AboutUsRecyclerInformation> data = Collections.emptyList();


    public AboutUsRecyclerAdapter(Context context, List<AboutUsRecyclerInformation> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public AboutUsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.about_recycler_custom_row, parent, false);
        AboutUsViewHolder holder = new AboutUsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AboutUsViewHolder holder, int position) {

        AboutUsRecyclerInformation current = data.get(position);
        holder.txtQues.setText(current.ques);
        holder.txtAns.setText(current.ans);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class AboutUsViewHolder extends RecyclerView.ViewHolder {

        TextView txtQues;
        TextView txtAns;

        public AboutUsViewHolder(View itemView) {
            super(itemView);

            txtQues = (TextView) itemView.findViewById(R.id.txtQues);
            txtAns = (TextView) itemView.findViewById(R.id.txtAns);

        }

    }

}
