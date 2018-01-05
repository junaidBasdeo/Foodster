package com.example.junaid.foodster;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

/**
 * Created by Junaid
 * Users added here are to be assumed as admin.  The purpose of add screen
 * is to allow admins to be created by the original admin since the normal
 * signup will only create reviewers
 */
public class AddUserActivity extends BaseActivity {
    private View ourActivityView;
    private boolean alreadyScaled = false;
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.adduser);

         try {
            TextView userAccount = findViewById(R.id.newUserName);
        } catch (Exception e)
        {

        }
        try {

        } catch (Exception disconnectException)
        {
            Log.e("Info",  "Exception Generated in method adduser onCreate " + disconnectException.getMessage() + " Exception Type : " + disconnectException.getClass().toString());

        }
    }
    public void hideAddUser(View v) {
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

    public void addUserNow(View v)
    {
        try {

            EditText newUserName = findViewById(R.id.newUserName);
            EditText newUserPassword = findViewById(R.id.newUserPassword);
            EditText newUserPasswordValidate = findViewById(R.id.newUserPasswordValidate);

            // make sure password is right
            if (newUserPassword.getText().toString().equals(newUserPasswordValidate.getText().toString())) {

                DatabaseController dbcon= new DatabaseController(this);
                UserTypeController dbconType = new UserTypeController(this);

                dbcon.open();
                dbconType.open();

                // only add users who do not exist.  otherwise report not added
                // does our profile exist
                String selectQuery = "SELECT  * FROM " + "users" + " WHERE "
                        + "user_name" + " = " + "'" + newUserName + "'";

                Cursor c = dbcon.rawQuery(selectQuery);
                if (c != null && c.moveToFirst()) {
                    Toast.makeText(getApplicationContext(), "User profile already registered.  Choose a new profile or login. ", Toast.LENGTH_LONG).show();

                } else {
                    dbcon.insertAdmin(newUserName.getText().toString(), newUserPassword.getText().toString());
                }

                String selectQueryType = "SELECT  * FROM " + "usertype" + " WHERE "
                        + "user_name" + " = '" + newUserName.getText().toString() + "'";

                Cursor cType = dbconType.rawQuery(selectQuery);
                String typeFromDB = "";

                if( c != null && c.moveToFirst() ) {

                } else
                {
                    dbconType.insert(newUserName.getText().toString(), "Admin");

                }
                Toast.makeText(getApplicationContext(), "User profile registered as an Admin. ", Toast.LENGTH_SHORT).show();
                dbcon.close();
                dbconType.close();
                finish();
            } else
            {
                Toast.makeText(getApplicationContext(), "Password validation not passed.  Please ensure your password and validating password match. ", Toast.LENGTH_LONG).show();
            }


        } catch (Exception disconnectException)
        {
            Log.e("Info",  "Exception Generated in method addUserNow " + disconnectException.getMessage() + " Exception Type : " + disconnectException.getClass().toString());

        }
    }
}