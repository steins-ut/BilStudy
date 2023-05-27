package com.merko.bilstudy.notepad.MindMapModels;

public class Connector {
    public Item item, parent;
    public int width, circRadius, arrowSize, argExt;
    public String color;
    public int location;

    public Connector(Item item, Item parent) {
        this.item = item;
        this.parent = parent;
    }
    public int getLocation(){
        return this.location;
    }
    public void setLocation(int location){
        this.location = location;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getCircRadius() {
        return circRadius;
    }

    public void setCircRadius(int circRadius) {
        this.circRadius = circRadius;
    }

    public int getArrowSize() {
        return arrowSize;
    }

    public void setArrowSize(int arrowSize) {
        this.arrowSize = arrowSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getArgExt() {
        return argExt;
    }

    public void setArgExt(int argExt) {
        this.argExt = argExt;
    }



}
