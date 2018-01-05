package com.example.junaid.foodster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.junaid.foodster.Globals.ApplicationGlobals;
import java.util.Calendar;

/**
 * Created by Junaid on 12/4/2017.
 */

public class LocalReviewActivity extends BaseActivity {

    private LocalReviewController dbCon= new LocalReviewController(this);
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();
    private LocalReviewHelper dbHelper = new LocalReviewHelper(this );
    private Business currentSelectedBusiness = ag.getbusinesses().get(ag.getbusinessClicked());
    private String currentBussinessID = currentSelectedBusiness.getID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_local_review);

        TextView rating_text = findViewById(R.id.rating_text);
        Button submit = findViewById(R.id.submit);

        rating_text.setText("Give review for : " + currentSelectedBusiness.getName());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment = findViewById(R.id.comment);
                RatingBar rb = findViewById(R.id.ratingBar);
                dbCon.open();
                boolean usersDBExist = dbHelper.doesDatabaseExist(getApplicationContext());
                if (!usersDBExist) {
                    // TODO : deal with unable to get DB
                    Log.e("LocalReview", "Unable to get DB for adding review");
                } else {
                    // TODO: Need to deal with bad insert better
                    String now = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    // insert a new review
                    dbCon.insert( ag.getloggedInName(), currentBussinessID, now, comment.getText().toString(), Float.toString(rb.getRating()));
                    // TODO : may not want to go back to main activity afte this
                    Intent i = new Intent(getApplicationContext(), YelpSearchActivity.class);
                    startActivity(i);

                }


            }
        });

    }

}

