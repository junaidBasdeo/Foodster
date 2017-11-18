package com.example.junaid.foodster;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

public class Resturant extends AppCompatActivity {

    private static final String URL_DATA ="https://developers.zomato.com/api/v2.1/search?entity_type=city&q=Storrs&count=20";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecycleViewData();
        }

        private void loadRecycleViewData(){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();

            StringRequest stringRequest =new StringRequest(Request.Method.GET,
                    URL_DATA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray array = jsonObject.getJSONArray("failed");

                                for(int i=0; i<array.length(); i++){
                                    JSONObject rest = array.getJSONObject(i);
                                    ListItems item = new ListItems(
                                            rest.getString("name"),
                                            rest.getString("aggregate_rating"),
                                            rest.getString("url"),
                                            rest.getString("photos_url")
                                    );

                                    adapter= new DataAdapter(listItems, getApplicationContext());
                                    recyclerView.setAdapter(adapter);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);




        }
    }



