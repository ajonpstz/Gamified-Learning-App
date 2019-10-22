package com.example.gamified_learning_app.data;

import java.util.Date;
import java.util.List;

public class User {
    public String  userName,
            description,
            email;
    public Date    dateJoined;
    public List<TaskList> taskLists;
}
