package com.example.gamified_learning_app.data;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public String description;
    
    public User(String email, String username) {
        this(email,username,"");
    }
    
    public User(String email, String username, String description) {
        this.username = username;
        this.email = email;
        this.description = description;
    }
}
