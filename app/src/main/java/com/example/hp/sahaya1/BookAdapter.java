package com.example.hp.sahaya1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private ArrayList<BookPojo> books;
    private int mViewResourceId;
    DBHelper dbHelper;
    TextView id, bookName, address;

    public BookAdapter(Context context, int textViewResourceId, ArrayList<BookPojo> books) {

        super(context, textViewResourceId, books);
        this.books = books;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {

        convertView = mInflater.inflate(mViewResourceId, null);
        BookPojo book_pojo = books.get(position);
        if (book_pojo != null) {
            id = (TextView) convertView.findViewById(R.id.record_id);
            bookName = (TextView) convertView.findViewById(R.id.book_name);
            address = (TextView) convertView.findViewById(R.id.address_location);
            id.setText(book_pojo.getId());
            bookName.setText(book_pojo.getBook_name());
            address.setText(book_pojo.getPlace());
        }
        return convertView;
    }
}