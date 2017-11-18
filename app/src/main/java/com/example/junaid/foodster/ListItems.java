package com.example.junaid.foodster;

/**
 * Created by Junaid on 11/8/2017.
 */

public class ListItems {
    private String head;
    private String Description;
    private String urlImage;
    private String reviewSample;

    public ListItems(String head, String description, String url,
                     String reviewSample) {
        this.head = head;
        this.Description = description;
        this.urlImage = url;
        this.reviewSample = reviewSample;
    }

    public String getHead() { return head; }
    public String getDescription() {return Description;}
    public String getUrlImage(){return urlImage;}
    public String getReviewSample(){return reviewSample;}

}
