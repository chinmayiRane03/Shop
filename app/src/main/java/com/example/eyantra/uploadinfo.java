package com.example.eyantra;


public class uploadinfo {

    public String imageName;
    public String imageURL;
    public String shopName;
    public String phone;
    public String shopAddress;
    public String shopLocality;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String key;



    public uploadinfo() {
        ///Empty Constructor needed
    }

    public uploadinfo(String imageName, String imageURL, String shopName, String phone, String shopAddress, String shopLocality, String phoneNo) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.shopName = shopName;
        this.phone = phone;
        this.shopAddress = shopAddress;
        this.shopLocality = shopLocality;

    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLocality() {
        return shopLocality;
    }

    public void setShopLocality(String shopLocality) {
        this.shopLocality = shopLocality;
    }



}
