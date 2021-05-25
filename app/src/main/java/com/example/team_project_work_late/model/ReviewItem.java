package com.example.team_project_work_late.model;

public class ReviewItem {
    private String UID;
    private String userName;
    private int rating;
    private String contents;
    private String photoURL;



    public void setUID(String UID){ this.UID = UID; }
    public String getUID() { return UID; }

    public void setUserName(String userName) { this.userName = userName; }
    public String getUserName() { return userName; }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getContents() {
        return contents;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
