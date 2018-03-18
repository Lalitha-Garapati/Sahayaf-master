package com.example.hp.sahaya1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OthersAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private ArrayList<OthersPojo> othersPojos;
    private int mViewResourceId;
    Context context;
    DBHelper dbHelper;
    TextView id, itemName, address;

    public OthersAdapter(Context context, int textViewResourceId, ArrayList<OthersPojo> othersPojos) {
        super(context, textViewResourceId, othersPojos);
        this.othersPojos = othersPojos;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId, null);
        OthersPojo othersPojo = othersPojos.get(position);
        if (othersPojo != null) {
            id = (TextView) convertView.findViewById(R.id.record_id);
            itemName = (TextView) convertView.findViewById(R.id.item_name);
            address = (TextView) convertView.findViewById(R.id.address_location);
            id.setText(othersPojo.getId());
            itemName.setText(othersPojo.getItem());
            address.setText(othersPojo.getPlace());
        }
        return convertView;
    }
}
