package com.merko.bilstudy.social;

public class OldStudies {
    String date;
    String type;
    int duration;
    int image;

    public OldStudies(String date, String type,int duration,int image) {
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.image = image;
    }

    public String getDate() { return date; }
    public String getType() {
        return type;
    }

    public int getImage() {
        return image;
    }

    public int getDuration() {
        return duration;
    }
}
