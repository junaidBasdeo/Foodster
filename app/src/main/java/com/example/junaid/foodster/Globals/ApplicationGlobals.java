package com.example.junaid.foodster.Globals;

import android.app.Activity;
import android.content.Context;

import com.example.junaid.foodster.Business;
import com.example.junaid.foodster.Review;

import java.util.ArrayList;

public class ApplicationGlobals {
    private static ApplicationGlobals instance;

    // Global variable

    // Restrict the constructor from being instantiated
    private ApplicationGlobals() {
    }

    public static synchronized void reInit() {
            instance = new ApplicationGlobals();
    }

    Boolean UserHasLoggedIn = false;
    public void setUserHasLoggedIn (Boolean d) {
        this.UserHasLoggedIn = d;
    }
    public Boolean getUserHasLoggedIn () {
        return this.UserHasLoggedIn ;
    }
    int UserLockoutTimeout = 0;
    public void setUserLockoutTimeout (int d) {
        this.UserLockoutTimeout = d;
    }
    public int getUserLockoutTimeout () {
        return this.UserLockoutTimeout ;
    }

    String consumer_key = "yGFFA716YJACxLqv-iX9DQ";
    public void setconsumer_key (String d) {
        this.consumer_key = d;
    }
    public String getconsumer_key () {
        return this.consumer_key ;
    }

    String accesstoken = "5KVfhZLLPB_PNGtEcQ8BcplufpqmzDoben8ymdWawrCO6k-K2WlLFF6Tep6hYnWYQCzkFVus9eZkk1VD7J1lgxcT9V-671htIS2rGNoie-bsIwZ24LWUMi77liIjWnYx";
    public void setaccesstoken (String d) {
        this.accesstoken = d;
    }
    public String getaccesstoken () {
        return this.accesstoken ;
    }

    String consumer_secret = "v3DMEfAz3MIZwqUQfqcTz8dDdnqzwR0xxr7UWthXcuGJoQ4GwyeIPTDQWXRNHyRr";
    public void setconsumer_secret (String d) {
        this.consumer_secret = d;
    }
    public String getconsumer_secret () {
        return this.consumer_secret ;
    }

    String endpointGetToken = "https://api.yelp.com/oauth2/token";
    public void setendpoint (String d) {
        this.endpointGetToken = d;
    }
    public String getendpoint () {
        return this.endpointGetToken ;
    }

    String tokenType = "Bearer";
    public void settokenType (String d) {
        this.tokenType = d;
    }
    public String gettokenType () {
        return this.tokenType ;
    }

    String grant_type = "client_credentials";
    public void setgrant_type (String d) {
        this.grant_type = d;
    }
    public String getgrant_type () {
        return this.grant_type ;
    }

    public static synchronized ApplicationGlobals getInstance() {
        if (instance == null) {
            instance = new ApplicationGlobals();
        }
        return instance;
    }
    Context TransProcessingActivityContext ;
    public void setTransProcessingActivityContext (Context d) {
        this.TransProcessingActivityContext = d;
    }
    public Context getTransProcessingActivityContext () {
        return this.TransProcessingActivityContext ;
    }

    Activity currentActivity;
    public void setcurrentActivity (Activity d) {
        this.currentActivity = d;
    }
    public Activity getcurrentActivity () {
        return this.currentActivity ;
    }

    float userSelectedLandscapeScaling = 1f;
    public void setuserSelectedLandscapeScaling (float d) {
        this.userSelectedLandscapeScaling = d;
    }
    public float getuserSelectedLandscapeScaling () {
        return this.userSelectedLandscapeScaling ;
    }

    int systemRotation = 0;
    public void setsystemRotation (int d) {
        this.systemRotation = d;
    }
    public int getsystemRotation () {
        return this.systemRotation ;
    }

    int systemOrientation = 0;
    public void setsystemOrientation (int d) {
        this.systemOrientation = d;
    }
    public int getsystemOrientation () {
        return this.systemOrientation ;
    }

    String loggedInName = "";
    public void setloggedInName (String d) {
        this.loggedInName = d;
    }
    public String getloggedInName () {
        return this.loggedInName ;
    }

    String loggedInUserType = "";
    public void setloggedInUserType (String d) {
        this.loggedInUserType = d;
    }
    public String getloggedInUserType () {
        return this.loggedInUserType ;
    }

    String partialAuthMessage = "";
    public void setpartialAuthMessage (String d) {
        this.partialAuthMessage = d;
    }
    public String getpartialAuthMessage () {
        return this.partialAuthMessage ;
    }

    ArrayList<Business> businesses = new ArrayList<>();
    public void setbusinesses (ArrayList<Business> d) {
        this.businesses = d;
    }
    public ArrayList<Business> getbusinesses () {
        return this.businesses ;
    }

    int businessClicked = 0;
    public void setbusinessClicked (int d) {
        this.businessClicked = d;
    }
    public int getbusinessClicked () {
        return this.businessClicked ;
    }

    ArrayList<Review> reviews = new ArrayList<>();
    public void setreviews (ArrayList<Review> d) {
        this.reviews = d;
    }
    public ArrayList<Review> getreviews () {
        return this.reviews ;
    }

    ArrayList<Review> localreviews = new ArrayList<>();
    public void setlocalreviews (ArrayList<Review> d) {
        this.localreviews = d;
    }
    public ArrayList<Review> getlocalreviews () {
        return this.localreviews ;
    }

}
