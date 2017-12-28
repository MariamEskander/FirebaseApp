package com.mariamnarouz.firebaseapp.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mariam.Narouz on 12/26/2017.
 */
@IgnoreExtraProperties
public class User {
    private String id;
    private String name ;
    private String email ;


    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
