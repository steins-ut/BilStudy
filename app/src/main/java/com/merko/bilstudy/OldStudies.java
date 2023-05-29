package com.merko.bilstudy;

public class OldStudies {
    String date;
    String study;
    int image;

    public OldStudies(String date,String study,int image) {
        this.date = date;
        this.study = study;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }

    public String getStudy() {
        return study;
    }
}
