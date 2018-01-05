package com.example.junaid.foodster;

import java.util.ArrayList;

/**
 * Created by Junaid on 11/8/2017.
 */

public class ReviewItems {
    private String  ReviewText;
    private ReviewUser ReviewNameData;
    private String Rating;
    private String ReviewDate;


    public ReviewItems(String reviewtext, String rating ,ReviewUser  reviewnamedata, String reviewdate
    ) {
        this.ReviewNameData = reviewnamedata;
        this.ReviewText = reviewtext;
        this.Rating = rating;
        this.ReviewDate = reviewdate;
    }

    public ReviewUser getReviewNameData() {return ReviewNameData;}
    public String  getReviewText() { return ReviewText; }
    public String getRating(){return Rating;}
    public String getReviewDate() { return ReviewDate; }


}