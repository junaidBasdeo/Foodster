package com.example.junaid.foodster;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

import java.io.IOException;

/**
 * Created by Junaid
 * Users added here are to be assumed as reviewers and must have full data assigned
 */

public class Signup extends Activity {

    private DatabaseController dbCon= new DatabaseController(this);
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();
    private DatabaseHelper dbHelper = new DatabaseHelper(this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Button addUser = findViewById(R.id.addUser);
        Button signonInsteadButton = findViewById(R.id.signoninstead);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignupClick(v);
            }
        });

        signonInsteadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Signup.class);
                startActivity(i);


            }
        });
    }



    public void onSignupClick(View v) {
        dbCon.open();

        if(v.getId()== R.id.addUser) {

            EditText firstin = findViewById(R.id.firstin);
            EditText lastin = findViewById(R.id.lastin);
            EditText userin = findViewById(R.id.usernamein);
            EditText passin = findViewById(R.id.passin);
            EditText rePassin = findViewById(R.id.rePassin);
            EditText emailin = findViewById(R.id.emailin);

            String firstName = firstin.getText().toString();
            String lastName = lastin.getText().toString();
            String userName = userin.getText().toString();
            String password = passin.getText().toString();
            String repassword = rePassin.getText().toString();
            String email = emailin.getText().toString();


            if(!password.equals(repassword)){
                Toast pass= Toast.makeText(Signup.this, "Password does not match", Toast.LENGTH_SHORT);
                pass.show();
            }
            else {
                boolean usersDBExist = dbHelper.doesDatabaseExist(getApplicationContext());
                if (!usersDBExist) {
                    // TODO : deal with unable to get DB
                    Log.e("Signup", "Unable to get DB for adding user");
                } else {
                    // TODO: Need to deal with bad insert better
                    // insert a new user
                    dbCon.insert(firstName, lastName, email, userName, password);
                    // TODO : add error handling in casee the insert does not work
                    // go to primary content view
                    ag.setUserHasLoggedIn(true);
                    ag.setloggedInName(userName);
                    // default to reviewer for signups
                    ag.setloggedInUserType("ReviewerModel");

                    // TODO : may not want to go back to main activity afte this
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                 }
            }
        }
    }

    @Override
    public void onBackPressed() {

        // swallow the key
    }
    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first


    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first

        // Save the anything we want to come back to without redoing
        ContentValues values = new ContentValues();

    }

    public void registerNewUser(View v)
    {
        try {
            // go back to our screen... may not use this method... is here if we display things after register.. the intent may be changed once
            // a user registration is complete
            Intent intentLogin = new Intent(Signup.this, Signup.class);
            startActivity(intentLogin);

        } catch (Exception e) {

        }
    }


}