package com.softwarica.lostandfound.module;

public class Human {
    private String fullname;
    private String locationfound;
    private String age;
    private String sex;
    private String phone;
    private String details;
    private String image;
    private String status;

    public Human(String fullname, String locationfound, String age, String sex, String phone, String details, String image, String status) {
        this.fullname = fullname;
        this.locationfound = locationfound;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.details = details;
        this.image = image;
        this.status = status;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocationfound() {
        return locationfound;
    }

    public void setLocationfound(String locationfound) {
        this.locationfound = locationfound;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
