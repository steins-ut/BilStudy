package com.merko.bilstudy.shop;
import java.util.UUID;

public class ShopItem {
    String type;
    String name;
    String price;

    int image;
    UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public static final String TABLE_NAME = "shop";




    public ShopItem(String type, String name, String price, int image) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.uuid = UUID.randomUUID();
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
