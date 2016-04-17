package com.example.david.hackathon;

/**
 * Created by mouth on 4/16/16.
 */
public class PostInfo {

    private String userID;
    private String title;
    private String content;
    private int pushCount;

    public PostInfo(String title, String userID, String content) {
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.pushCount = 0;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getPushCount() {
        return pushCount;
    }

    public void newPush() {
        pushCount++;
    }

}
