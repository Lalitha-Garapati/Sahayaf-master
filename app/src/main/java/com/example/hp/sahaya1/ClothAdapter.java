package com.example.hp.sahaya1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ClothAdapter extends ArrayAdapter {

    DBHelper dbHelper;
    private int mViewResourceId;
    private LayoutInflater mInflater;
    private ArrayList<ClothesPojo> clothes;
    TextView id, gender, age_group, address;

    public ClothAdapter(Context context, int textViewResourceId, ArrayList<ClothesPojo> clothes) {

        super(context, textViewResourceId, clothes);
        this.clothes = clothes;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {

        convertView = mInflater.inflate(mViewResourceId, null);
        ClothesPojo clothesPojo = clothes.get(position);
        if( clothesPojo != null) {
            id = (TextView) convertView.findViewById(R.id.record_id);
            gender = (TextView) convertView.findViewById(R.id.gender_type);
            age_group = (TextView)convertView.findViewById(R.id.age_group) ;
            address = (TextView) convertView.findViewById(R.id.address_location);

            id.setText(clothesPojo.getId());
            gender.setText(clothesPojo.getGender());
            age_group.setText(clothesPojo.getAge_group());
            address.setText(clothesPojo.getPlace());
        }
        return convertView;
    }
}