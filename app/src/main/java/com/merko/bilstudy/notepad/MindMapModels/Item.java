package com.merko.bilstudy.notepad.MindMapModels;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.HashMap;

public class Item extends androidx.appcompat.widget.AppCompatEditText {

    Context context;
    OnTouchListener onTouchListener;
    ArrayList<Item> topChildItems = new ArrayList<>();
    ArrayList<Item> bottomChildItems = new ArrayList<>();
    ArrayList<Item> rightChildItems = new ArrayList<>();
    ArrayList<Item> leftChildItems = new ArrayList<>();
    ArrayList<Connector> connectors = new ArrayList<>();
    HashMap<Item, Integer>  parents = new HashMap<>();

    public Item(Context context) {
        super(context);
        this.context = context;
        this.setHint("Write your thoughts:");
        this.setGravity(Gravity.CENTER);
        this.setPadding(20,15,20,15);
    }

    public Item(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void assignOnTouchListener(OnTouchListener onTouchListener){
        this.onTouchListener = onTouchListener;
    }

    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public void addTopChild(Item item){
        topChildItems.add(item);
    }
    public ArrayList<Item> getTopChildItems(){
        return topChildItems;
    }
    public Item getTopChildByIndex(int index){
        return topChildItems.get(index);
    }

    public void addBottomChild(Item item){
        bottomChildItems.add(item);
    }
    public ArrayList<Item> getBottomChildItems(){
        return bottomChildItems;
    }
    public Item getBottomChildByIndex(int index){
        return bottomChildItems.get(index);
    }

    public void addRightChild(Item item){
        rightChildItems.add(item);
    }
    public ArrayList<Item> getRightChildItems(){
        return rightChildItems;
    }
    public Item getRightChildByIndex(int index){
        return rightChildItems.get(index);
    }

    public void addLeftChild(Item item){
        leftChildItems.add(item);
    }
    public ArrayList<Item> getLeftChildItems(){
        return leftChildItems;
    }
    public Item getLeftChildByIndex(int index){
        return leftChildItems.get(index);
    }

    public void addToParentConnections(Item parent, Connector c){
        parent.getConnectors().add(c);
    }

    public void addParent(Item parent, int location){
        parents.put(parent, location);
    }

    public void addConnection(Item parent, int location, Connector connector){
        connector.setLocation(location);
        connectors.add(connector);
        addToParentConnections(parent, connector);
    }

    public ArrayList<Connector> getConnectors(){
        return this.connectors;
    }
    private void disableEditText() {
        this.setFocusable(false);
        this.setEnabled(false);
        this.setCursorVisible(false);
        this.setKeyListener(null);
        this.setBackgroundColor(Color.TRANSPARENT);
    }
    public void delete(){
        this.disableEditText();
        for(Connector c : connectors){
            c.setParent(null);
            c.setWidth(0);
        }
    }

}
