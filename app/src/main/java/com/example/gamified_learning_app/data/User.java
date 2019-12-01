package com.example.gamified_learning_app.data;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public String description;
    public long currency, premiumCurrency;
    
    public User() {}
    public User(String email, String username) {
        this(email,username,"");
    }
    
    public User(String email, String username, String description) {
        this(email,username,description,5,5);
    }
    
    public User(DocumentSnapshot snapshot) {
        this.email = snapshot.getString("email");
        this.username = snapshot.getString("username");
        this.description = snapshot.getString("description");
        Long currency = snapshot.getLong("currency");
        Long premiumCurrency = snapshot.getLong("premiumCurrency");
        this.currency = currency == null ? 0 : currency;
        this.premiumCurrency = premiumCurrency == null ? 0 : premiumCurrency;
    }
    
    public User(String email, String username, String description,
                long currency, long premiumCurrency) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.currency = currency;
        this.premiumCurrency = premiumCurrency;
    }
}
