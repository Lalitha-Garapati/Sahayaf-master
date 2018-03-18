package com.example.hp.sahaya1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

class FoodAdapter extends ArrayAdapter{

    private LayoutInflater mInflater;
    private ArrayList<FoodPojo> foods;
    private int mViewResourceId;
    DBHelper dbHelper;
    TextView id, itemName, quantity, quantity_units, address;

    public FoodAdapter(Context context, int textViewResourceId, ArrayList<FoodPojo> foods) {

        super(context, textViewResourceId, foods);
        this.foods = foods;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {

        convertView = mInflater.inflate(mViewResourceId, null);
        FoodPojo food_pojo = foods.get(position);
        if( food_pojo != null) {
            id = (TextView) convertView.findViewById(R.id.record_id);
            itemName = (TextView) convertView.findViewById(R.id.item_name);
            quantity = (TextView) convertView.findViewById(R.id.quantity_number);
            quantity_units = (TextView) convertView.findViewById(R.id.quantity_units);
            address = (TextView) convertView.findViewById(R.id.address_location);

            id.setText(food_pojo.getId());
            itemName.setText(food_pojo.getItem());
            quantity.setText(food_pojo.getQuantity());
            quantity_units.setText(food_pojo.getQuantity_units());
            address.setText(food_pojo.getPlace());
        }
        return convertView;
    }
}