package com.merko.bilstudy.social.shop;

public class ShopItem {
    String type;
    String name;
    String price;

    int image;
    int id;

    public int getId(){return id;}

    public static final String TABLE_NAME = "shop";


    public ShopItem(String type, String name, String price, int image, int id) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return Integer.parseInt(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
