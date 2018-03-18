package com.example.hp.sahaya1;

import android.graphics.Bitmap;

public class BookPojo {

    private String id;
    private String book_name;
    private String mobile;
    private String place;
    private String image;

    public BookPojo(String id, String book_name, String place, String mobile, String image) {
        this.id = id;
        this.book_name = book_name;
        this.place = place;
        this.mobile = mobile;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPlace() {
        return place;
    }

    public String getImage() {
        return image;
    }
}
