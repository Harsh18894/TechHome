package com.techHome.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.imageview.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import com.squareup.picasso.Picasso;
import com.techHome.R;
import com.techHome.constants.ContactRecyclerInformation;
import com.techHome.constants.ProfileRecyclerInformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Harsh on 12-May-16.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactViewHolder> {

    private LayoutInflater inflater;
    List<ContactRecyclerInformation> data = Collections.emptyList();
    private Context context;

    public ContactRecyclerAdapter(Context context, List<ContactRecyclerInformation> data) {
        this.context = context;
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
        holder.txtName.setText(current.name);
        holder.txtAddress.setText(current.pos);
        Picasso picasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
        picasso.load(data.get(position).getImgPhoto()).placeholder(R.mipmap.ic_time_black).error(R.drawable.no_image).fit().centerInside().into(holder.imgPhoto);
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
