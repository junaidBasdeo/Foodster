package com.example.junaid.foodster;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {


    DatabaseController dbconUsers = new DatabaseController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        dbconUsers.open();

        // force a reset for testing.  comment out when we like it
        dbconUsers.startOver(dbconUsers);
        String passwordCheck = "";
        boolean usersDBExist = DatabaseHelper.doesDatabaseExist(this);
        if (!usersDBExist) {
            Toast.makeText(getApplicationContext(), "No database.  Exiting", Toast.LENGTH_SHORT).show();
            //TO DO:
            // gracefully exit the app
        } else {

            boolean usersTableExist = DatabaseHelper.doesTableExist(dbconUsers);
            if (usersTableExist) {
                DatabaseHelper helper = new DatabaseHelper(this);

                Button login = (Button) findViewById(R.id.loginBtn);
                Button signup = (Button) findViewById(R.id.signupBtn);

                EditText UserName = (EditText) findViewById(R.id.username);
                EditText Password = (EditText) findViewById(R.id.paswordTxt);

                String uName = UserName.getText().toString();
                String pWord = Password.getText().toString();

                switch (v.getId()) {
                    // only check password if the user does exist and didn't push login
                    case R.id.loginBtn: {
                        if (pWord.equals(passwordCheck)) {
                            Intent i = new Intent(this, Resturant.class);
                            startActivity(i);
                        } else {
                            Toast wrongPass = Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT);
                            wrongPass.show();
                        }

                        String selectQuery = "SELECT  password FROM " + "users" + " WHERE "
                                + "username" + " = " + "'" + uName + "'";

                        Cursor c = dbconUsers.rawQuery(selectQuery);
                        if (c != null && c.moveToFirst()) {
                            passwordCheck = c.getString(0);
                        } else {
                            // make it a random GUID so no one can login by guessing the no password found
                            passwordCheck = UUID.randomUUID().toString();
                        }


                        // if we had no match then password will obviously not match and we fail
                    }
                    case R.id.signupBtn: {
                        Intent i = new Intent(this, Signup.class);
                        startActivity(i);
                    }
                }
            }
        }
    }
}

