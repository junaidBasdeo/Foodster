package com.example.junaid.foodster;

import java.util.ArrayList;

/**
 * Created by Junaid on 12/2/2017.
 */

    public class Review  {

    private String text;
    private ReviewUser user;
    private String rating;
    private String timeCreated;
    private String url;

    public Review(String text, ReviewUser user, String rating,
                    String timeCreated, String url) {
        this.text = text;
        this.user = user;
        this.rating = rating;
        this.timeCreated = timeCreated;
        this.url = url;

    }

    public String getText() {
        return text;
    }

    public ReviewUser getUser() {
        return user;
    }

    public String getRating() {
        return  rating;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public String getURL(){
        return url;
    }

}