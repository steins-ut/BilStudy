package com.merko.bilstudy.shop;

public class ShopItem {
    String type;
    String name;
    String price;
    int image;

    public ShopItem(String type, String name, String price, int image) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
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

    public String getPrice() {
        return price;
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