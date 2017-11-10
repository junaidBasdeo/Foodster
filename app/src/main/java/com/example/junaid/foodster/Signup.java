package com.example.junaid.foodster;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

    DatabaseController dbController= new DatabaseController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onSignupClick(View v) {

        if(v.getId()== R.id.addUser) {

            EditText firstin = (EditText) findViewById(R.id.firstin);
            EditText lastin = (EditText) findViewById(R.id.lastin);
            EditText userin = (EditText) findViewById(R.id.usernamein);
            EditText passin = (EditText) findViewById(R.id.passin);
            EditText rePassin = (EditText) findViewById(R.id.rePassin);
            EditText emailin = (EditText)findViewById(R.id.emailin);

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
                // insert a new user
                dbController.insert(firstName, lastName, email, userName, password);
                // TODO : add error handling in casee the insert does not work

                // TODO : may not want to go back to main activity afte this
                Intent i = new Intent(this, Resturant.class);
                startActivity(i);
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