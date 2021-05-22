package com.example.team_project_work_late.model;

public class ReviewItem {
    private String UID;
    private String userName;
    private float rating;
    private String contents;



    public void setUID(String UID){ this.UID = UID; }
    public String getUID() { return UID; }

    public void setUserName(String userName) { this.userName = userName; }
    public String getUserName() { return userName; }

    public void setRating(float rating) {
        this.rating = rating;
    }
    public float getRating() {
        return rating;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getContents() {
        return contents;
    }
}
