package com.example.junaid.foodster;

import java.util.ArrayList;

/**
 * Created by Junaid on 12/4/2017.
 */


public class ReviewUser  {
    private String imageUrl;
    private String name;
    private String website;


    public ReviewUser(String imageUrl, String name) {
        this.name = name;
        this.imageUrl = imageUrl;

    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageUrl;
    }


}