package com.techHome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.neopixl.pixlui.components.textview.TextView;
import com.techHome.R;

/**
 * Created by Dell on 4/14/2016.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] options;
    private int[] colors = new int[]{0xffcae1ff, 0xffa4d3ee, 0xff8ee5ee, 0xffd1eeee, 0xff99cccc};

    public GridAdapter(Context context, String[] options) {
        this.mContext = context;
        this.options = options;
    }


    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_dashboard_grid_single_item, null);
            int colorPos = position % colors.length;
            grid.setBackgroundColor(colors[colorPos]);
            TextView txtGridItem = (TextView) grid.findViewById(R.id.txtGridItem);
            txtGridItem.setText(options[position]);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
