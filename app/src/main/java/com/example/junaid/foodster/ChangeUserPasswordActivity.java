package com.example.junaid.foodster;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

/**
 * Created by Junaid
 * Allow us to promote a reviewer to admin or demote admin to reviewer
 */

public class ChangeUserPasswordActivity   extends BaseActivity {
    private View ourActivityView;
    private boolean alreadyScaled = false;
    private int userIDWeAreActingOn = 0;
    private String userNameWeAreChanging = "";
    private String userTypeWeAreChanging = "";
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();



    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.changepassword);
        ourActivityView = findViewById(android.R.id.content);

        TextView userAccount = findViewById(R.id.userLoggedInName);
        EditText changeUserName = findViewById(R.id.changeUserName);

        try {

            // TODO: add handling for administator validaiton so not just anyone can run it
            //if (getloggedInUserType().equals("Administrator")) {

                EditText currentUserPassword = findViewById(R.id.currentUserPassword);
                // default to changable
                currentUserPassword.setEnabled(true);
                TextView textView18 = findViewById(R.id.textView18);
                currentUserPassword.setVisibility(View.GONE);
                textView18.setVisibility(View.GONE);

                // handle user passed in case it was
                Bundle b = getIntent().getExtras();
                if (b.getString("UserID") != null)

                {
                    userIDWeAreActingOn = Integer.parseInt(b.getString("UserID"));

                    DatabaseController dbcon = new DatabaseController(this);
                    dbcon.open();
                    String selectQuery = "SELECT  user_id, user_name, user_password FROM " + "users" + " WHERE "
                            + "user_id" + " = '" + String.valueOf(userIDWeAreActingOn) + "'";

                    String foundUser = "";
                    // get name of user
                    Cursor c = dbcon.rawQuery(selectQuery);
                    if (c != null && c.moveToFirst()) {
                        userNameWeAreChanging = c.getString(1);
                        changeUserName.setText(c.getString(1));
                        // stop it from being changed
                        changeUserName.setEnabled(false);
                        foundUser = c.getString(1);

                        // get type of user
                        UserTypeController dbconType = new UserTypeController(getApplicationContext());
                        dbconType.open();
                        String selectQueryType = "SELECT  user_type FROM " + " USERTYPE " + " WHERE "
                                + "usertype_name" + " = '" + foundUser + "'";

                        Cursor cType = dbconType.rawQuery(selectQueryType);
                        if (cType != null && cType.moveToFirst()) {
                            userTypeWeAreChanging = cType.getString(0);
                        }

                        dbconType.close();
                    }
                    dbcon.close();

                 }

            // TODO: add handling for administator validaiton so not just anyone can run it
//            } else {
//                // set non admin to themself and don't allow change to that
//                changeUserName.setText(ag.getloggedInName());
//                changeUserName.setEnabled(false);
//             }

        } catch (Exception e)
        {

        }

        try {

        } catch (Exception disconnectException)
        {
            Log.e("Info",  "Exception Generated in method ChangeUserPasswordActivity onCreate " + disconnectException.getMessage() + " Exception Type : " + disconnectException.getClass().toString());

        }
    }
    public void hideChangeUserPassword(View v) {
        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
        finish();
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

    public void changeUserPasswordNow(View v)
    {
        try {

            EditText changeUserName = findViewById(R.id.changeUserName);
            EditText currentUserPassword = findViewById(R.id.currentUserPassword);
            EditText changeUserPassword = findViewById(R.id.changeUserPassword);
            EditText changeUserPasswordValidate = findViewById(R.id.changeUserPasswordValidate);

            if (!changeUserName.getText().toString().equals("admin")) {
                // make sure password is right
                if (changeUserPassword.getText().toString().equals(changeUserPasswordValidate.getText().toString())) {

                    DatabaseController dbcon = new DatabaseController(this);

                    dbcon.open();

                    // only add users who do not exist.  otherwise report not added
                    // does our profile exist
                    String selectQuery = "SELECT  user_id, user_name, user_password FROM " + "users" + " WHERE "
                            + "user_name" + " = " + "'" + changeUserName.getText().toString() + "'";

                    Cursor c = dbcon.rawQuery(selectQuery);
                    if (c != null && c.moveToFirst()) {
                        int changeUserID = c.getInt(0);
                        String changeUserOriginalPassword = c.getString(2);


                        // TODO: add handling for administator validaiton so not just anyone can run it
                        //if (currentUserPassword.getText().toString().equals(changeUserOriginalPassword) || ag.getloggedInUserType().equals("Administrator")) {

                            dbcon.update(changeUserID, changeUserName.getText().toString(), changeUserPassword.getText().toString());

                            Toast.makeText(getApplicationContext(), "User Password Changed. ", Toast.LENGTH_SHORT).show();
                            dbcon.close();
                            finish();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Current Password Entered Wrong.  No changes made ", Toast.LENGTH_LONG).show();
//                            Log.e("Info",  "Current Password Entered Wrong.  No changes made");
//                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Profile does not exit.  No changes made ", Toast.LENGTH_LONG).show();
                        Log.e("Info",  "Profile does not exit.  No changes made ");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Password validation not passed.  Please ensure your password and validating password match. ", Toast.LENGTH_LONG).show();
                    Log.e("Info",  "Password validation not passed.  Please ensure your password and validating password match.");
                }
            }  else
            {
                Toast.makeText(getApplicationContext(), "The administrator password cannont be changed. ", Toast.LENGTH_LONG).show();
                Log.e("Info",  "The administrator password cannont be changed. ");

            }


        } catch (Exception disconnectException)
        {
            Log.e("Info",  "Exception Generated in method changeUserPasswordNow " + disconnectException.getMessage() + " Exception Type : " + disconnectException.getClass().toString());

        }
    }

}