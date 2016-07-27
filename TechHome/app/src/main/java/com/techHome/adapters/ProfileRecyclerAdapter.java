package com.techHome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.constants.ProfileRecyclerInformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dell on 4/29/2016.
 */
public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ProfileViewHolder> {

    private LayoutInflater inflater;
    List<ProfileRecyclerInformation> data = Collections.emptyList();


    public ProfileRecyclerAdapter(Context context, List<ProfileRecyclerInformation> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.profile_recycler_custom_row, parent, false);
        ProfileViewHolder holder = new ProfileViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {

        ProfileRecyclerInformation current = data.get(position);
        holder.txtAttribute.setText(current.getAttribute());
        holder.txtValue.setText(current.getValue());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ProfileViewHolder extends RecyclerView.ViewHolder {

        TextView txtAttribute;
        TextView txtValue;

        public ProfileViewHolder(View itemView) {
            super(itemView);

            txtAttribute = (TextView) itemView.findViewById(R.id.txtAttribute);
            txtValue = (TextView) itemView.findViewById(R.id.txtValue);

        }

    }
}
