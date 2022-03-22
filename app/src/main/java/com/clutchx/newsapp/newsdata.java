package com.clutchx.newsapp;

public class newsdata {
    private String Headline,Description,Imgurl,time,date,category,Key;
    public  newsdata(){

    }

    public newsdata(String headline, String description, String imgurl, String time, String date, String category, String key) {
        Headline = headline;
        Description = description;
        Imgurl = imgurl;
        this.time = time;
        this.date = date;
        this.category = category;
        Key = key;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
