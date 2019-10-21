package com.example.gamified_learning_app.data;

import java.util.Date;
import java.util.List;

public class User {
    String  userName,
            description,
            email;
    Date    dateJoined;
    List<TaskList> taskLists;
}
