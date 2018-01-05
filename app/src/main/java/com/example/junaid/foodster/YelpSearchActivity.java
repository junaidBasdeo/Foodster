package com.example.junaid.foodster;

/**
 * Created by Junaid on 12/2/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.junaid.foodster.Globals.ApplicationGlobals;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class YelpSearchActivity extends BaseActivity {

    private ApplicationGlobals ag = ApplicationGlobals.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            super.onCreate(savedInstanceState);

            setContentView(R.layout.yelp_search);
            Button yelpSearchButton = findViewById(R.id.yelpSearchButton);

            yelpSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText searchTerm = findViewById(R.id.searchTerm);
                    EditText searchLocation = findViewById(R.id.searchLocation);

                    try {
                        getYelpBusinesses(searchTerm.getText().toString(), searchLocation.getText().toString());
                    } catch (IOException ioe) {
                        Log.e("Info", "Exception Generated in method onClickListener YelpSearchActivity " + ioe.getMessage() + " Exception Type : " + ioe.getClass().toString());

                    }

                }
            });
        } catch (Exception e) {
            Log.e("Info", "Exception Generated in method  onCreate " + e.getMessage() + " Exception Type : " + e.getClass().toString());

        }

    }

    public void getYelpBusinesses(String searchTerm, String searchLocation) throws IOException {
        // overriding logic for UI async task just for ease of use
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Map<String, String> parms = new HashMap<>();
        parms.put("term", searchTerm);
        parms.put("location", searchLocation);

        // Build our URL for rest post
        // example :
        // https://api.yelp.com/v3/businesses/search?term=restaurant&location=boulder&Authorization=Bearer [YOUR ACCESS TOKEN]
        // there is a space between URL and teh access token for a reason
        Request.Builder builder = new Request.Builder();
        builder.url("https://api.yelp.com/v3/businesses/search?term=" + searchTerm + "&location=" + searchLocation);
        builder.get();
        Request request = builder.build();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + ag.getaccesstoken())
                        .addHeader("content-type", "multipart/form-data;")
                        .addHeader("cache-control", "no-cache")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        // GET /businesses/search
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        // TODO : Handle JSON
                        int responseCode = 0;
                        if ((responseCode = response.code()) == 200) {
                            // do something wih the result
                            String jsonData = response.body().string();
                            // Get response object
                            ArrayList<Business> businesses = processResults(jsonData);
                            ag.setbusinesses(businesses);

                            Intent i = new Intent(getApplicationContext(), BusinessListActivity.class);
                            startActivity(i);
                        } else {
                            Log.e("Message", "Unsuccessful call with response code : " + responseCode);
                        }
                    }
                }

                public ArrayList<Business> processResults(String jsonData) {
                    ArrayList<Business> businesses = new ArrayList<>();
                    try {


                        JSONObject yelpJSON = new JSONObject(jsonData);
                        JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                        for (int i = 0; i < businessesJSON.length(); i++) {
                            JSONObject businessJSON = businessesJSON.getJSONObject(i);
                            String name = businessJSON.getString("name");
                            String phone = businessJSON.optString("display_phone", "Phone not available");
                            String website = businessJSON.getString("url");
                            double rating = businessJSON.getDouble("rating");

                            String id = businessJSON.getString("id");
                            String imageUrl = businessJSON.getString("image_url");

                            double latitude = businessJSON.getJSONObject("coordinates").getDouble("latitude");

                            double longitude = businessJSON.getJSONObject("coordinates").getDouble("longitude");

                            ArrayList<String> address = new ArrayList<>();
                            JSONArray addressJSON = businessJSON.getJSONObject("location")
                                    .getJSONArray("display_address");
                            for (int y = 0; y < addressJSON.length(); y++) {
                                address.add(addressJSON.get(y).toString());
                            }

                            ArrayList<String> categories = new ArrayList<>();
                            JSONArray categoriesJSON = businessJSON.getJSONArray("categories");

                            for (int y = 0; y < categoriesJSON.length(); y++) {
                                categories.add(categoriesJSON.getJSONObject(y).getString("title"));
                            }
                            Business business = new Business(name, phone, website, rating,
                                    imageUrl, address, latitude, longitude, categories, id);
                            businesses.add(business);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return businesses;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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

