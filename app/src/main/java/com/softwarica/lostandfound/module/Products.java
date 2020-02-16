package com.softwarica.lostandfound.module;

public class Products {

    private String productname;
    private String location;
    private String phone;
    private String details;
    private String image;
    private String status;

    public Products(String productname, String location, String phone, String details, String image, String status) {
        this.productname = productname;
        this.location = location;
        this.phone = phone;
        this.details = details;
        this.image = image;
        this.status = status;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
