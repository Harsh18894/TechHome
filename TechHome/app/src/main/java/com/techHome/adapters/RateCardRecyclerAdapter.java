package com.techHome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.constants.RatesRecyclerInformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Harsh on 7/25/2016.
 */
public class RateCardRecyclerAdapter extends RecyclerView.Adapter<RateCardRecyclerAdapter.RatesViewHolder> {

    private LayoutInflater inflater;
    List<RatesRecyclerInformation> data = Collections.emptyList();

    public RateCardRecyclerAdapter(Context context, List<RatesRecyclerInformation> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rates_recycler_custom_row, parent, false);
        RatesViewHolder holder = new RatesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RatesViewHolder holder, int position) {
        RatesRecyclerInformation ratesRecyclerInformation = data.get(position);
        holder.txtCity.setText(ratesRecyclerInformation.city);
        holder.txtPrice.setText(ratesRecyclerInformation.charges);
        holder.txtCond.setText(ratesRecyclerInformation.condition);
        holder.txtInfo.setText(ratesRecyclerInformation.information);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class RatesViewHolder extends RecyclerView.ViewHolder {

        TextView txtCity;
        TextView txtPrice;
        TextView txtCond;
        TextView txtInfo;

        public RatesViewHolder(View itemView) {
            super(itemView);

            txtCity = (TextView) itemView.findViewById(R.id.txtCity);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtCond = (TextView) itemView.findViewById(R.id.txtCond);
            txtInfo = (TextView) itemView.findViewById(R.id.txtInfo);

        }
    }
}