package com.example.junaid.foodster;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import com.example.junaid.foodster.Globals.ApplicationGlobals;

/**
 * Created by Junaid
 * Separate login page to make login and signup different and allow more complicated login handling such as lock out and attempts
 */

public class Login extends AppCompatActivity {
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();
    // login attempts
    private int counter = 3;
    public static final String MyPREFERENCES = "SupportHomePreferences" ;
    private SharedPreferences sp = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private Time startTimeOfLockout;
    private boolean userIsLockedOut = false;
    private Button loginButton;
    private SharedPreferences.Editor editor;
    // get the DB
    private UserTypeController dbconType = new UserTypeController(this);
    private UserTypeHelper uHelper = new UserTypeHelper(this);
    private DatabaseController dbcon = new DatabaseController(this);
    // ensure db is created
    private DatabaseHelper dbHelper = new DatabaseHelper(this );

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.login);
        // app prefs
        sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ag.setcurrentActivity(this);
        try {
            TextView userAccount = findViewById(R.id.userLoggedInName);
            userAccount.setText(ag.getloggedInName() + " - " + ag.getloggedInUserType());
        } catch (Exception e)
        {

        }


        // get our editor
        editor = sp.edit();
        String currentDateandTime = sdf.format(new java.util.Date());
        Log.e("Info",  "Login Requested" + currentDateandTime);

        try {
            // default to not logged in
            ag.setUserHasLoggedIn(sp.getBoolean("UserHasLoggedIn", false));

        } catch (Exception e) {
            Log.e("Info",  "Exception In Initial setup (shared prefs section) " + e.getMessage().toString());

        }

        try {
            dbcon.open();
            dbconType.open();
            //dbcon.startOver(dbcon);
            //dbconType.startOver(dbconType);
            // does our user DB exist
            boolean usersDBExist = dbHelper.doesDatabaseExist(getApplicationContext());
            if (!usersDBExist) {
            } else {
                // does our user table exist
                boolean usersTableExist = dbHelper.doesTableExist(dbcon);
                if (usersTableExist) {
                    // does our admin profile exist
                    String selectQuery = "SELECT  * FROM " + "users"
                            + " WHERE " + "username" + " = " + "'admin'";

                    Cursor c = dbcon.rawQuery(selectQuery);
                    if (c != null && c.moveToFirst()) {

                    } else {

                        // TODO : remove hardcoded default
                        String adminInitPassword = "P@$$W0rd";

                        dbcon.insertAdmin("admin", adminInitPassword);
                    }

                    // does our user table exist
                    boolean usersTypeTableExist = uHelper.doesTableExist(dbconType);
                    if (usersTypeTableExist) {
                        // does our admin profile exist in the admin group type
                        String selectGroupQuery = "SELECT  * FROM " + "usertype"
                                + " WHERE " + "usertype_name" + " = " + "'admin'";

                        Cursor cType = dbconType.rawQuery(selectGroupQuery);
                        if (cType != null && cType.moveToFirst()) {

                        } else {
                            dbconType.insert("admin", "Administrator");
                        }
                    }


                }
            }


            if (!ag.getUserHasLoggedIn()) {
                setContentView(R.layout.login);

                Button signupInsteadButton = findViewById(R.id.signupinstead);
                loginButton = findViewById(R.id.loginButton);
                final EditText userName = findViewById(R.id.userName);
                final EditText password = findViewById(R.id.password);

                Button cancelButton = findViewById(R.id.cancelButton);
                final TextView attemptsMessage = findViewById(R.id.attemptsMessage);
                attemptsMessage.setVisibility(View.GONE);

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // get our handle to the dataset
                        String[] coloumns = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_UNAME,
                                DatabaseHelper.COLUMN_PASS};
                        // calling elements    in an array
                        String selectQuery = "SELECT  id, username, password FROM " + "users";
                              //  + " WHERE " + "username" + " = '" + userName.getText().toString() + "'";

                        Cursor c = dbcon.rawQuery(selectQuery);
                        String passwordFromDB = "";
                        int userIDFromDB = 0;

                        // get our handle to the dataset
                        String[] coloumnsType = new String[]{UserTypeHelper.USERTYPE_ID, UserTypeHelper.USERTYPE_NAME,
                                UserTypeHelper.USER_TYPE};
                        // calling elements    in an array
                        String selectQueryType = "SELECT  usertype_id, usertype_name, user_type FROM " + "usertype" + " WHERE "
                                + "usertype_name" + " = '" + userName.getText().toString() + "'";

                        Cursor cType = dbconType.rawQuery(selectQueryType);
                        String typeFromDB = "";

                        if (cType != null && cType.moveToFirst() )
                        {
                            typeFromDB = cType.getString(2);
                            // save our type for use in the app as we move around and security is checked
                            ag.setloggedInUserType(typeFromDB);
                        } else
                        {
                            // default unknown type to lowest level
                            ag.setloggedInUserType("Guest");
                        }


                        if( c != null && c.moveToFirst() ) {
                            passwordFromDB = c.getString(2);
                            userIDFromDB = c.getInt(0);
                            // user .... initially hard coded
                            if (password.getText().toString().equals(passwordFromDB)) {
                                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                                // go to primary content view
                                ag.setUserHasLoggedIn(true);
                                editor.putBoolean("UserHasLoggedIn", true);
                                ag.setloggedInName(userName.getText().toString());

                                dbcon.close();
                                dbconType.close();



                                // restart activity for user support
                                Intent intentLogin = new Intent(Login.this, MainActivity.class);
                                startActivity(intentLogin);


                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                editor.putBoolean("UserHasLoggedIn", false);
                                ag.setUserHasLoggedIn(false);
                                ag.setloggedInName("");

                                attemptsMessage.setVisibility(View.VISIBLE);
                                attemptsMessage.setBackgroundColor(Color.RED);
                                // keep track of attempts
                                counter--;
                                attemptsMessage.setText(Integer.toString(counter));

                                if (counter == 0) {
                                    loginButton.setEnabled(false);
                                    Toast.makeText(getApplicationContext(), "Too Many Attempts.  You are prevented from logging in for a period of time.", Toast.LENGTH_SHORT).show();
                                    startTimeOfLockout = new Time();
                                    startTimeOfLockout.setToNow();
                                }
                                userIsLockedOut = true;

                                Handler handlerTimer = new Handler();
                                // cancel wait after 2 minutes delay
                                if (ag.getUserLockoutTimeout() > 0) {
                                    handlerTimer.postDelayed(runnableTimer, ag.getUserLockoutTimeout());

                                } else {
                                    // default to 15 min
                                    handlerTimer.postDelayed(runnableTimer, 900000);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No user records found to match user", Toast.LENGTH_SHORT).show();

                        }

                }
                });

                signupInsteadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Signup.class);
                        startActivity(i);


                    }
                });

                // if user cancels we have to exit in order to protect app
                // recylce globals to protect last user activity
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Activity actToKill = ag.getcurrentActivity();

                        ApplicationGlobals.reInit();

                        dbcon.close();
                        dbconType.close();
                        // let go of resources
                        actToKill.finishAffinity();
                        System.exit(0);
                    }
                });
            } else {
                finish();
            }

            editor.apply();

        } catch (Exception e) {
            dbcon.close();
            dbconType.close();
            Log.e("Info",  "Exception Generated in method login_activity " + e.getMessage() + " Exception Type : " + e.getClass().toString());

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

    // timer
    private Runnable runnableTimer = new Runnable() {
        @Override
        public void run() {
            // make sure we are alive
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new java.util.Date());
            Log.e("Info",  "Lockout Period Expired " + currentDateandTime);
            loginButton.setEnabled(true);
            Toast.makeText(getApplicationContext(), "You are now allowed to login.  Lockout period expired.", Toast.LENGTH_SHORT).show();
            Log.e("Info",  "Lockout expiration and allowing of login button initiated by Timer");
            // reset attempts
            counter = 3;

        }
    };

}
