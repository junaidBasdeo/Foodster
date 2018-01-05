package com.example.junaid.foodster;

import java.util.ArrayList;

/**
 * Created by Junaid on 11/8/2017.
 */

public class BusinessItems {
    private ArrayList<String>  Address;
    private String Name;
    private String Review;
    private String UrlImage;
    private String WebSite;
    private String Phone;

    public BusinessItems(String name, ArrayList<String> address, String review , String  phone, String image, String url
                     ) {
        this.Name = name;
        this.Address = address;
        this.Review = review;
        this.Phone = phone;
        this.UrlImage = image;
        this.WebSite = url;
    }

    public String getName() {return Name;}
    public ArrayList<String>  getAddress() { return Address; }
    public String getReview(){return Review;}
    public String getPhone() { return Phone; }
    public String getUrlImage(){return UrlImage;}
    public String getWebsite(){return WebSite;}

}
