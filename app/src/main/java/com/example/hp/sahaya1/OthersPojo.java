package com.example.hp.sahaya1;

import android.graphics.Bitmap;

public class OthersPojo {

    private String id;
    private String item;
    private String mobile;
    private String place;
    private String imageName;

    public OthersPojo(String id, String item, String place, String mobile, String imageName) {
        this.id = id;
        this.item = item;
        this.mobile = mobile;
        this.place = place;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPlace() {
        return place;
    }

    public String getImageName() {
        return imageName;
    }
}