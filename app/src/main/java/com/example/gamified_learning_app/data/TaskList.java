package com.example.gamified_learning_app.data;

import java.util.List;

public class TaskList {
    String  owner,
            title,
            description;
    List<String>    flags;
    List<Task>      tasks;
    float   rating;
    int     numberRated;
}
