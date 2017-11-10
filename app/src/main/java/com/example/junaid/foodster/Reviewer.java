package com.example.junaid.foodster;

/**
 * Created by Junaid on 10/11/2017.
 */
public class Reviewer {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String User;

    public int userName() {
        return id;
    }

    public void setUserName(String user) {
        this.User = User;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String fname) {
        this.firstname = firstname;
    }

    public String getlastName() {
        return lastname;
    }

    public void setlastName(String lname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
