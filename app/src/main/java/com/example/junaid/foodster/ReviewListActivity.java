package com.example.junaid.foodster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import android.widget.TextView;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junaid on 12/4/2017.
 */

public class ReviewListActivity extends AppCompatActivity {

    private RecyclerView.Adapter localadapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ArrayList<Business> businesses = new ArrayList<>();
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();

    private List<ReviewItems> reviewItems;
    private List<ReviewItems> localreviewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewslist_activity);

        TextView name = findViewById(R.id.name);
        TextView address = findViewById(R.id.address);
        TextView review = findViewById(R.id.review);
        TextView urlimage = findViewById(R.id.urlimage);
        TextView website = findViewById(R.id.website);
        TextView phone = findViewById(R.id.phone);

        businesses = ag.getbusinesses();
        Business currentSelectedBusiness = businesses.get(ag.getbusinessClicked());
        // set header data for the review detail page
        name.setText(currentSelectedBusiness.getName());
        review.setText("Overall User Rating : " + Double.toString(currentSelectedBusiness.getRating()));
        urlimage.setText(Html.fromHtml("<a href=\"" + currentSelectedBusiness.getImageUrl() + "\">View Image</a>"));
        website.setText(Html.fromHtml("<a href=\"" + currentSelectedBusiness.getWebsite() + "\">Visit Business Website</a>"));
        phone.setText(currentSelectedBusiness.getPhone());
        String workAddress = "";
        for (int x = 0; x < currentSelectedBusiness.getAddress().size(); x++) {
            workAddress = workAddress + currentSelectedBusiness.getAddress().get(x) + "\n";
        }

        address.setText(workAddress);

        // Local Reviews
        ArrayList<Review> reviewsLocal = ag.getlocalreviews();

        RecyclerView recycleViewLocal = findViewById(R.id.recycleViewLocal);
        recycleViewLocal.setHasFixedSize(true);
        recycleViewLocal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Add Header
        recycleViewLocal.addItemDecoration(new HeaderDecoration(this,
                recycleViewLocal,  R.layout.localreviewheader));

        localreviewItems = new ArrayList<>();


        for (int i = 0; i < reviewsLocal.size(); i++) {
            ReviewUser reviewernamedata = reviewsLocal.get(i).getUser();
            String reviewRating = reviewsLocal.get(i).getRating();
            String reviewtext = reviewsLocal.get(i).getText();
            String reviewdate = reviewsLocal.get(i).getTimeCreated();
            ReviewItems reviewItem = new ReviewItems(
                    reviewtext,
                    reviewRating,
                    reviewernamedata,
                    reviewdate
            );
            localreviewItems.add(reviewItem);
        }

        localadapter= new ReviewAdapter(localreviewItems, getApplicationContext());
        recycleViewLocal.setAdapter(localadapter);



        ArrayList<Review> reviewsFromWeb = ag.getreviews();

            recyclerView = findViewById(R.id.recycleView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            reviewItems = new ArrayList<>();

        // Add Header
        recyclerView.addItemDecoration(new HeaderDecoration(this,
                recyclerView,  R.layout.yelpreviewsheader));


        for (int i = 0; i < reviewsFromWeb.size(); i++) {
                ReviewUser reviewernamedata = reviewsFromWeb.get(i).getUser();
                String reviewRating = reviewsFromWeb.get(i).getRating();
                String reviewtext = reviewsFromWeb.get(i).getText();
                String reviewdate = reviewsFromWeb.get(i).getTimeCreated();
                ReviewItems reviewItem = new ReviewItems(
                        reviewtext,
                        reviewRating,
                        reviewernamedata,
                        reviewdate
                );
                reviewItems.add(reviewItem);
            }

        adapter= new ReviewAdapter(reviewItems, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }
}

