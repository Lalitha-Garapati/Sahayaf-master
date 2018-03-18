package com.example.hp.sahaya1;

public class FoodPojo {

    private String id;
    private String quantity;
    private String quantity_units;
    private String item;
    private String mobile;
    private String place;
    private String imageName;

    public FoodPojo(String id, String item, String quantity, String quantity_units, String place,
                    String mobile, String imageName) {

        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.quantity_units = quantity_units;
        this.mobile = mobile;
        this.place = place;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPlace() {
        return place;
    }

    public String getMobile() {
        return mobile;
    }

    public String getItem() {
        return item;
    }

    public String getQuantity_units() {
        return quantity_units;
    }

    public String getImageName() {
        return imageName;
    }
}
