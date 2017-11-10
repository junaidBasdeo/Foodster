package com.example.junaid.foodster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Resturant extends AppCompatActivity {

    private static final String URL_DATA = https://api.yelp.com/v2/business/yelp-san-francisco;
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

        for(int i=0; i<=50; i++){
            ListItems listItem= new ListItems(
                "heading"+ (i+1),
                    "test text"
            );

            listItems.add(listItem);

            }

            adapter= new DataAdapter(listItems, this);

            recyclerView.setAdapter(adapter);
        }
    }



