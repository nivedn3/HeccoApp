package com.example.hecco;

public class feedback {

    String phone;
    String rating;
    String action;
    String time;

    public feedback() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public feedback(String phone, String rating, String action, String time) {
        this.phone = phone;
        this.rating = rating;
        this.action = action;
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
