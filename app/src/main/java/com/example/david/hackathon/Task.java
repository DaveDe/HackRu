package com.example.david.hackathon;

public class Task {

    private String status;
    private int secondsAgo;
    private int push;


    public Task(String status, int secondsAgo, int push){
        this.status = status;
        this.secondsAgo = secondsAgo;
        this.push = push;
    }

}
