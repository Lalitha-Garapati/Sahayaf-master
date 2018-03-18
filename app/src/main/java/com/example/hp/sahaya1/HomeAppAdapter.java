package com.example.hp.sahaya1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

class HomeAppAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private ArrayList<HomeAppPojo> homeAppPojos;
    private int mViewResourceId;
    DBHelper dbHelper;
    TextView id, itemName, address;

    public HomeAppAdapter(Context context, int textViewResourceId, ArrayList<HomeAppPojo> homeAppPojos) {

        super(context, textViewResourceId, homeAppPojos);
        this.homeAppPojos = homeAppPojos;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {

        convertView = mInflater.inflate(mViewResourceId, null);
        HomeAppPojo homeAppPojo = homeAppPojos.get(position);
        if( homeAppPojo != null) {
            id = (TextView) convertView.findViewById(R.id.record_id);
            itemName = (TextView) convertView.findViewById(R.id.item_name);
            address = (TextView) convertView.findViewById(R.id.address_location);
            id.setText(homeAppPojo.getId());
            itemName.setText(homeAppPojo.getItem());
            address.setText(homeAppPojo.getPlace());
        }
        return convertView;
    }
}