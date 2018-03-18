package com.example.hp.sahaya1;

public class ClothesPojo {

    private String id;
    private String itemName;
    private String mobile;
    private String place;
    private String gender;
    private String age_group;
    private String imageName;

    public ClothesPojo(String id, String gender, String age_group, String place,
                       String mobile, String imageName) {

        this.id = id;
        this.mobile = mobile;
        this.place = place;
        this.gender = gender;
        this.age_group = age_group;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPlace() {
        return place;
    }

    public String getGender() {
        return gender;
    }

    public String getAge_group() {
        return age_group;
    }

    public String getImageName() {
        return imageName;
    }
}
