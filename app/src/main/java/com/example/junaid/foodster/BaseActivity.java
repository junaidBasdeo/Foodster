package com.example.junaid.foodster;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

/**
 * Created by Junaid on 12/4/2017.
 */

public class BaseActivity  extends AppCompatActivity {
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();


    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Foodster");
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        try {
            Log.e("MainActivity", "Logged In User : " + ag.getloggedInName());
            Log.e("MainActivity", "Logged In User Type : " + ag.getloggedInUserType());

            // only show menus if the user is logged in
            if (ag.getUserHasLoggedIn()) {
                // adjust our menus based on user ability
                if (ag.getloggedInUserType().equals("ReviewerModel")) {
                    menu.findItem(R.id.addUserMenuOption).setEnabled(false);
                    menu.findItem(R.id.changeUserTypeMenuOption).setEnabled(false);
                    menu.findItem(R.id.changeUserPasswordMenuOption).setEnabled(false);
                    menu.findItem(R.id.exit).setEnabled(true);
                    menu.findItem(R.id.searchMenuOption).setEnabled(true);

                } else if (ag.getloggedInUserType().equals("Administrator")) {
                    menu.findItem(R.id.addUserMenuOption).setEnabled(true);
                    menu.findItem(R.id.changeUserTypeMenuOption).setEnabled(true);
                    menu.findItem(R.id.changeUserPasswordMenuOption).setEnabled(true);
                    menu.findItem(R.id.exit).setEnabled(true);
                    menu.findItem(R.id.searchMenuOption).setEnabled(true);

                } else {
                    menu.findItem(R.id.addUserMenuOption).setEnabled(false);
                    menu.findItem(R.id.changeUserTypeMenuOption).setEnabled(false);
                    menu.findItem(R.id.changeUserPasswordMenuOption).setEnabled(false);
                    menu.findItem(R.id.exit).setEnabled(true);
                    menu.findItem(R.id.searchMenuOption).setEnabled(false);
                }
            } else
            {
                menu.findItem(R.id.addUserMenuOption).setEnabled(false);
                menu.findItem(R.id.changeUserTypeMenuOption).setEnabled(false);
                menu.findItem(R.id.changeUserPasswordMenuOption).setEnabled(false);
                menu.findItem(R.id.exit).setEnabled(true);
                menu.findItem(R.id.searchMenuOption).setEnabled(false);
            }
        } catch (Exception e) {
            //("\n" + "Exception Generated in method initializeApplication " + e.getMessage() + " Exception Type : " + e.getClass().toString());
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.addUserMenuOption:
                addUserMenuOption();
                return true;
            case R.id.changeUserTypeMenuOption:
                changeUserTypeMenuOption();
                return true;
            case R.id.changeUserPasswordMenuOption:
                changeUserPasswordMenuOption();
                return true;
            case R.id.exit:
                finish();
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.loginBtn: {
                    Intent i = new Intent(this, Login.class);
                    startActivity(i);
                    return;
                }
                case R.id.signupBtn: {
                    Intent i = new Intent(this, Signup.class);
                    startActivity(i);
                    return;
                }
            }
        } catch (Exception e) {

            Log.i("Exception", "Exception Generated in method onCLick " + e.getMessage() + " Exception Type : " + e.getClass().toString());
        }
    }

    private void addUserMenuOption()
    {
        //("\n" + "Entering addUserMenuOption", LogLevel.Debug_Log);
        try {

            Intent intentLogin = new Intent(getApplicationContext(), AddUserActivity.class);
            startActivity(intentLogin);


        } catch (Exception e) {
            Log.i("Exception", "Exception Generated in method addUserMenuOption " + e.getMessage() + " Exception Type : " + e.getClass().toString());
        }
    }
    private void changeUserTypeMenuOption()
    {
        //("\n" + "Entering changeAUserType", LogLevel.Debug_Log);
        try {

            Intent intentLogin = new Intent(BaseActivity.this, ChangeUserTypeActivity.class);
            startActivity(intentLogin);


        } catch (Exception e) {
            //("\n" + "Exception Generated in method changeAUserType " + e.getMessage() + " Exception Type : " + e.getClass().toString());
        }
    }

    private void changeUserPasswordMenuOption()
    {
        //("\n" + "Entering changeAUserPassword", LogLevel.Debug_Log);
        try {

            Intent intentLogin = new Intent(BaseActivity.this, ChangeUserPasswordActivity.class);
            startActivity(intentLogin);


        } catch (Exception e) {
            //("\n" + "Exception Generated in method changeAUserPassword " + e.getMessage() + " Exception Type : " + e.getClass().toString());
        }
    }


    // TODO: fill out these actions

    private void refreshRestaurantDataMenuOption() {}
}
