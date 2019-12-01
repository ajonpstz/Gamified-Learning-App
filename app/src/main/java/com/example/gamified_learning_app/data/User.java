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
        this(snapshot.getString("email"), snapshot.getString("username"),
            snapshot.getString("description"),
            snapshot.getLong("currency") == null ? 0 : snapshot.getLong("currency"),
            snapshot.getLong("premiumCurrency") == null ? 0 : snapshot.getLong("premiumCurrency")
            );
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
