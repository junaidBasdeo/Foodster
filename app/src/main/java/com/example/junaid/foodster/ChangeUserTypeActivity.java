package com.example.junaid.foodster;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

public class ChangeUserTypeActivity   extends BaseActivity {
    private View ourActivityView;
    private boolean alreadyScaled = false;
    private int userIDWeAreActingOn = 0;
    private String userNameWeAreChanging = "";
    private String userTypeWeAreChanging = "";
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.changeusertype);

        // force user to be logged in to use this activity
        ourActivityView = findViewById(android.R.id.content);

        try {
            // set header
            TextView userAccount = findViewById(R.id.userLoggedInName);
            EditText changeUserTypeName = findViewById(R.id.changeUserTypeName);
            // default to changable
            changeUserTypeName.setEnabled(true);
            Spinner newUserType = findViewById(R.id.newUserType);


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
                    changeUserTypeName.setText(c.getString(1));
                    // stop it from being changed
                    changeUserTypeName.setEnabled(false);
                    foundUser = c.getString(1);

                    // get type of user
                    UserTypeController dbconType = new UserTypeController(getApplicationContext());
                    dbconType.open();
                    String selectQueryType = "SELECT  user_type FROM " + " USERTYPE " + " WHERE "
                            + "usertype_name" + " = '" + foundUser + "'";

                    Cursor cType = dbconType.rawQuery(selectQueryType);
                    if (cType != null && cType.moveToFirst()) {
                        userTypeWeAreChanging = cType.getString(0);
                        // set position for the value we have of this user type
                        newUserType.setSelection(((ArrayAdapter<String>)newUserType.getAdapter()).getPosition(userTypeWeAreChanging));
                    }

                    dbconType.close();
                }
                dbcon.close();

            }

        } catch (Exception e)
        {

        }

        try {

        } catch (Exception disconnectException)
        {
            Log.e("Info", "Exception Generated in method ChangeUserTypeActivity onCreate " + disconnectException.getMessage() + " Exception Type : " + disconnectException.getClass().toString());

        }
    }
    public void hideChangeUserType(View v) {
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

    public void changeUserTypeNow(View v)
    {
        try {


            EditText changeUserTypeName = findViewById(R.id.changeUserTypeName);
            Spinner user_type = findViewById(R.id.newUserType);

                UserTypeController dbconType = new UserTypeController(this);
                dbconType.open();
                String selectQueryType = "SELECT  * FROM " + "usertype" + " WHERE "
                        + "usertype_name" + " = '" + changeUserTypeName.getText().toString() + "'";

                Cursor cType = dbconType.rawQuery(selectQueryType);

                    if (cType != null && cType.moveToFirst() )
                    {
                        int userTypeID = cType.getInt(0);
                        dbconType.update(userTypeID, changeUserTypeName.getText().toString(), user_type.getSelectedItem().toString());
                    }
                Toast.makeText(getApplicationContext(), "User type changed. ", Toast.LENGTH_SHORT).show();
                dbconType.close();
                dbconType.close();
                finish();

        } catch (Exception disconnectException)
        {
            //("\n" + "Exception Generated in method changeUserTypeNow " + disconnectException.getMessage() + " Exception Type : " + disconnectException.getClass().toString());

        }
    }

}