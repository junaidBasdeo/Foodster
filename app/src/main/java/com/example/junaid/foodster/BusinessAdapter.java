package com.example.junaid.foodster;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Junaid on 11/8/2017.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {


    private LocalReviewController dbCon;
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();
    private LocalReviewHelper dbHelper;
    private List<BusinessItems> businessItems;
    private Context context;
    ArrayList<Business> businesses = new ArrayList<>();

    public BusinessAdapter(List<BusinessItems> businessItems, Context context) {
        this.businessItems = businessItems;
        this.context = context;
        dbCon= new LocalReviewController(context);
        dbCon.open();
        dbHelper = new LocalReviewHelper(context );
    }

    @Override
    public BusinessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BusinessAdapter.ViewHolder holder, int position) {
        BusinessItems listItem = businessItems.get(position);

        holder.phone.setText(listItem.getPhone());
        String workAddress = "";
        for (int x = 0; x<listItem.getAddress().size(); x++)
        {
                workAddress = workAddress + listItem.getAddress().get(x) + "\n";
        }
        holder.address.setText(workAddress);
        holder.name.setText(listItem.getName());
        holder.review.setText(listItem.getReview());
        holder.urlimage.setText(Html.fromHtml(listItem.getUrlImage()));
        holder.website.setText(Html.fromHtml(listItem.getWebsite()));

    }

    @Override
    public int getItemCount() {
        return businessItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        public TextView address;
        public TextView name;
        public TextView urlimage;
        public TextView review;
        public TextView website;
        public TextView phone;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

            address = itemView.findViewById(R.id.address);
            name= itemView.findViewById(R.id.name);
            review = itemView.findViewById(R.id.review);
            urlimage= itemView.findViewById(R.id.urlimage);
            website= itemView.findViewById(R.id.website);
            phone= itemView.findViewById(R.id.phone);        }

        @Override
        public void onClick(View v) {
            ag.setbusinessClicked(getAdapterPosition());
            Toast.makeText(v.getContext(), "short click position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            businesses = ag.getbusinesses();
            // Build our URL for rest post
            // example :
            // https://api.yelp.com/v3/businesses/{id}/reviews
            // there is a space between URL and teh access token for a reason
            Request.Builder builder = new Request.Builder();
            builder.url("https://api.yelp.com/v3/businesses/" + businesses.get(ag.getbusinessClicked()).getID() + "/reviews");
            builder.get();
            Request request = builder.build();

            // does our user table exist
            boolean reviewLocalTableExist = dbHelper.doesTableExist(dbCon);
            if (reviewLocalTableExist) {
                // get local reviews
                Cursor mCursor = dbCon.rawQuery("SELECT business_yelp_id, localreviewer, localrating, " +
                        "localtext, date FROM LOCALREVIEWS WHERE business_yelp_id = '" +
                        businesses.get(ag.getbusinessClicked()).getID() + "'");
                ArrayList<Review> mArrayList = new ArrayList<Review>();
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    mArrayList.add(new Review(
                            mCursor.getString(mCursor.getColumnIndex(LocalReviewHelper.USER_TEXT)),
                            new ReviewUser("", mCursor.getString(mCursor.getColumnIndex(LocalReviewHelper.USER_NAME))),
                            mCursor.getString(mCursor.getColumnIndex(LocalReviewHelper.USER_RATING)),
                            mCursor.getString(mCursor.getColumnIndex(LocalReviewHelper.USER_DATE)),
                            ""
                    ));
                    mCursor.moveToNext();
                }

                ag.setlocalreviews(mArrayList);
            }

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
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
                                ArrayList<Review> reviews = processResults(jsonData);
                                ag.setreviews(reviews);

                                dbCon.close();

                                Intent i = new Intent(context, ReviewListActivity.class);
                                context.startActivity(i);
                            } else {
                                Log.e("Message", "Unsuccessful call with response code : " + responseCode);
                            }
                        }


                    }

                    public ArrayList<Review> processResults(String jsonData) {
                        ArrayList<Review> reviews = new ArrayList<>();
                        try {
                            JSONObject yelpJSON = new JSONObject(jsonData);
                            JSONArray reviewsJSON = yelpJSON.getJSONArray("reviews");
                            for (int i = 0; i < reviewsJSON.length(); i++) {
                                JSONObject reviewJSON = reviewsJSON.getJSONObject(i);
                                String text = reviewJSON.getString("text");
                                String reviewdate = reviewJSON.optString("time_created", "Phone not available");
                                String url = reviewJSON.getString("url");
                                String rating = reviewJSON.getString("rating");

                                ReviewUser user = new ReviewUser(reviewJSON.getJSONObject("user").optString("url", "Phone not available") , reviewJSON.getJSONObject("user").optString("name", "Name Not Available"));

                                Review review = new Review(text, user, rating, reviewdate, url);
                                reviews.add(review);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return reviews;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            ag.setbusinessClicked(getAdapterPosition());
            Toast.makeText(v.getContext(), "long click position = " + getPosition(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
