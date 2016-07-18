package com.techHome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.imageview.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;
import com.techHome.constants.ContactRecyclerInformation;
import com.techHome.constants.ProfileRecyclerInformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rishabh on 12-May-16.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactViewHolder> {

    private LayoutInflater inflater;
    List<ContactRecyclerInformation> data = Collections.emptyList();

    public ContactRecyclerAdapter(Context context, List<ContactRecyclerInformation> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_recycler_custom_row, parent, false);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactRecyclerAdapter.ContactViewHolder holder, int position) {
        ContactRecyclerInformation current = data.get(position);
        holder.txtName.setText(current.Name);
        holder.txtAddress.setText(current.Address);
        holder.imgPhoto.setImageResource(current.imgPhoto);
       }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtAddress;
        ImageView imgPhoto;

        public ContactViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
        }
    }
}
