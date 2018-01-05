package com.example.junaid.foodster;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

import java.util.ArrayList;
import java.util.List;

public class BusinessListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ArrayList<Business> businesses = new ArrayList<>();
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();

    private List<BusinessItems> businessItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getApplicationContext()));

        businessItems = new ArrayList<>();
        businesses = ag.getbusinesses();

        for (int i = 0; i < businesses.size(); i++) {
            String businessName = businesses.get(i).getName();
            String rating = "Overall User Rating : " + Double.toString(businesses.get(i).getRating());
            String image = "<a href=\"" + businesses.get(i).getImageUrl() + "\">View Image</a>" ;
            String website = "<a href=\"" +  businesses.get(i).getWebsite() + "\">Visit Business Website</a>";
            String Header = Integer.toString(i);
            String Phone = businesses.get(i).getPhone();
            ArrayList<String> address = businesses.get(i).getAddress();
            BusinessItems listItem = new BusinessItems(
                    businessName,
                     address,
                     rating,
                    Phone,
                     image,
                    website

            );
            businessItems.add(listItem);
        }
        adapter= new BusinessAdapter(businessItems, getApplicationContext());
        recyclerView.setAdapter(adapter);

        }

    }





