package com.merko.bilstudy.notepad.MindMapModels;

//The classes MindMapView, Connector, Item and ItemLocation are
//the idea of JagarYousef, which can be found from the link https://github.com/JagarYousef/Mindo.
//This code for BilStudy adjusts a large portion of his work "mindmappinglibrary" to fit for a better
//UI experience.
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.merko.bilstudy.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MindMapView extends RelativeLayout {

    private Context context;
    private Activity activity;
    private RadioGroup radioGroup;
    private ArrayList<Item> allItems = new ArrayList<>();
    private ArrayList<Connector> topItems = new ArrayList<>();
    private ArrayList<Connector> leftItems = new ArrayList<>();
    private ArrayList<Connector> rightItems = new ArrayList<>();
    private ArrayList<Connector> bottomItems = new ArrayList<>();
    private int connectionWidth = 10, connectionArrowSize = 30, connectionCircRadius = 20, connectionArgSize = 30;
    private String connectionColor = "#000000";
    private MindMapView mindMapView;
    private OnClickListener onClickListener;
    private boolean onTouchNulled = false;
    private boolean isOnConnect = false;
    private boolean wasClickedOnce = false;
    private Item temp1;
    private Item temp2;
    private OnClickListener storeOnItemClicked;
    private GradientDrawable shape;

    public MindMapView(Context context) {
        super(context);
        this.context = context;
        this.activity = (Activity) context;
        mindMapView = this;
        radioGroup = findViewById(R.id.toolbar);
        this.setOnItemClicked(new itemClickedListener());
        shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 16, 16, 16, 16, 0, 0, 0, 0 });
        shape.setColor(Color.GRAY);
        shape.setStroke(3, Color.GREEN);

    }

    public MindMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activity = (Activity) context;
        mindMapView = this;
        radioGroup = findViewById(R.id.toolbar);
        this.setOnItemClicked(new itemClickedListener());
        shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 16, 16, 16, 16, 0, 0, 0, 0 });
        shape.setCornerRadius(50);
        shape.setColor(0xFFFFE500);
        shape.setStroke(10, Color.TRANSPARENT);

    }

    public MindMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.activity = (Activity) context;
        mindMapView = this;
        radioGroup =  findViewById(R.id.toolbar);
        this.setOnItemClicked(new itemClickedListener());
        shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 16, 16, 16, 16, 0, 0, 0, 0 });
        shape.setColor(Color.GRAY);
        shape.setStroke(3, Color.GREEN);

    }



    //Getters & Setters
    public void setRadioGroup(RadioGroup radioGroup){
        this.radioGroup = radioGroup;
    }

    public void setConnectionColor(String connectionColor) {
        this.connectionColor = connectionColor;
    }

    public void setConnectionWidth(int connectionWidth) {
        this.connectionWidth = connectionWidth;
    }

    public void setConnectionArrowSize(int connectionArrowSize) {
        this.connectionArrowSize = connectionArrowSize;
    }

    public void setConnectionCircRadius(int connectionCircSize) {
        this.connectionCircRadius = connectionCircSize;
    }
    public ArrayList<Item> getAllItems(){return this.allItems;}

    @Override
    protected void onMeasure (int widthMeasureSpec,
                              int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
    public void onRadioButtonClicked(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        if(radioID == R.id.connect){
            isOnConnect = true;
            onClickListener = storeOnItemClicked;
            if(onTouchNulled == false){
                for(Item box : allItems){
                    box.setOnTouchListener(null);
                    box.setFocusable(false);
                    box.setOnClickListener(onClickListener);
                }
            }
            onTouchNulled = true;

        }
        else if(radioID == R.id.edit){
            onClickListener = null;
            isOnConnect = false;
                for(Item box : allItems){
                    if(onTouchNulled == false){
                    box.setOnTouchListener(null);
                    }
                    box.setFocusableInTouchMode(true);
                    box.setOnClickListener(onClickListener);
                }
            onTouchNulled = true;

        }
        else if(radioID == R.id.drag){
            onClickListener = null;
            isOnConnect = false;
            for(Item box : allItems){
                box.setOnTouchListener(box.getOnTouchListener());
                box.setFocusable(false);
                box.setOnClickListener(onClickListener);
            }
            onTouchNulled = false;

        }
        else if(radioID == R.id.addRemove){
            onClickListener = storeOnItemClicked;
            isOnConnect = false;
                for(Item box : allItems){
                    box.setOnTouchListener(null);
                    box.setClickable(true);
                    box.setFocusable(false);
                    box.setOnClickListener(onClickListener);
                }
            onTouchNulled = true;

        }
    }
    class itemClickedListener implements OnClickListener{

        @Override
        public void onClick(View v) {


            if(isOnConnect == false){
                showMenu(v);
            }
            else{
                if(wasClickedOnce == false){
                    temp1 = (Item) v;
                    wasClickedOnce = true;
                }
                else{
                    temp2 = (Item) v;
                    if(temp1 == temp2){
                        //Do nothing, don't connect an item to itself
                    }
                    else{
                        Connector connector = new Connector(temp2, temp1);
                        bottomItems.add(connector);
                        temp1.addConnection(temp2, ItemLocation.BOTTOM, connector);
                    }
                    wasClickedOnce = false;
                }

            }
        }
    }

    //Adding the root item
    @SuppressLint("ClickableViewAccessibility")
    public void addCentralItem(Item item, boolean dragAble){
        allItems.add(item);
        item.setBackground(shape);
        item.setHint("Write your thoughts:");
        if (dragAble){
            dragItem(item);
        }
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        item.setLayoutParams(lp);
        item.setGravity(Gravity.CENTER);
        this.addView(item);

        item.setOnClickListener(onClickListener);

    }
    /*
    * ***This method is the work of JagarYousef, which is inside
    the mindmappinglibrary in the website https://github.com/JagarYousef/Mindo.***
    *
    Make any item drag able, This will make issues with
    a simple call of OnClickListener on the Item objects so you set it off to call the normal onclicklistener
    the custom OnItemClicked*/
    @SuppressLint("ClickableViewAccessibility")
    private void dragItem(final Item item) {

        OnTouchListener onTouchListener = new OnTouchListener() {
            float dX;
            float dY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float viewWidth= view.getWidth();
                float viewHeight = view.getHeight();
                float windowWidth = mindMapView.getWidth();
                float windowHeight = mindMapView.getHeight();


                    switch (motionEvent.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            dX = view.getX() - motionEvent.getRawX();
                            dY = view.getY() - motionEvent.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (windowWidth == viewWidth && windowHeight == viewHeight){}
                                  else {
                            view.animate()
                                    .x(motionEvent.getRawX() + dX)
                                    .y(motionEvent.getRawY() + dY)
                                    .setDuration(0)
                                    .start();

                            if (motionEvent.getRawX() + dX + viewWidth > windowWidth) {
                                view.animate()
                                        .x(windowWidth - viewWidth)
                                        .setDuration(0)
                                        .start();
                            }
                            if (motionEvent.getRawX() + dX < 0) {
                                view.animate()
                                        .x(0)
                                        .setDuration(0)
                                        .start();
                            }
                            if (motionEvent.getRawY() + dY + viewHeight > windowHeight) {
                                view.animate()
                                        .y(windowHeight - viewHeight)
                                        .setDuration(0)
                                        .start();
                            }
                            if (motionEvent.getRawY() + dY < 0) {
                                view.animate()
                                        .y(0)
                                        .setDuration(0)
                                        .start();
                            }

                            return true;
                        }
                        default:
                            item.setPressed(false);
                            return false;
                    }

                return true;
            }
        };
        item.setOnTouchListener(onTouchListener);
        item.assignOnTouchListener(onTouchListener);
    }

    //
    // * ***This method is the work of JagarYousef, which is inside
    //    the mindmappinglibrary in the website https://github.com/JagarYousef/Mindo.***
    //    * Adding an item that has the parent already on the view
    public void addItem(Item item, Item parent, int distance, int spacing, int location,
                        boolean dragAble){
        item.setBackground(shape);
        if (location == ItemLocation.TOP){

            this.addView(item);
            allItems.add(item);
            item.setOnClickListener(onClickListener);



            item.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            parent.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            item.setY(parent.getY() - (item.getMeasuredHeight() + distance));
            parent.addTopChild(item);

            if (parent.getTopChildItems().size() > 1){
                Item lastChildItem = parent.getTopChildByIndex(parent.getTopChildItems().size() - 2);
                lastChildItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                for (Item topItem : parent.getTopChildItems()){
                    topItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                    topItem.setX(topItem.getX() - (item.getMeasuredWidth()/2 + spacing));
                }
                item.setX(lastChildItem.getX() + lastChildItem.getMeasuredWidth() + spacing);
            }else{
                item.setX(parent.getX());
            }

            Connector connector = new Connector(item, parent);
            topItems.add(connector);
            item.addConnection(parent, ItemLocation.TOP, connector);

            if (dragAble)
                dragItem(item);

        }else if (location == ItemLocation.LEFT){
            this.addView(item);
            allItems.add(item);

            item.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            parent.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            item.setX(parent.getX() - (item.getMeasuredWidth() + distance));

            parent.addLeftChild(item);

            if (parent.getLeftChildItems().size() > 1){
                Item lastChildItem = parent.getLeftChildByIndex(parent.getLeftChildItems().size() - 2);
                lastChildItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                for (Item leftItem : parent.getLeftChildItems()){
                    leftItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                    leftItem.setY(leftItem.getY() - (item.getMeasuredHeight()/2 + spacing));
                }
                item.setY(lastChildItem.getY() + lastChildItem.getMeasuredHeight() + spacing);
            }
            else{
                item.setY(parent.getY());
            }

            Connector connector = new Connector(item, parent);
            leftItems.add(connector);
            item.addConnection(parent, ItemLocation.LEFT, connector);

            if (dragAble)
                dragItem(item);

        }else if (location == ItemLocation.RIGHT){
            this.addView(item);
            allItems.add(item);

            item.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            parent.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            item.setX(parent.getX() + (parent.getMeasuredWidth() + distance));

            parent.addRightChild(item);

            if (parent.getRightChildItems().size() > 1){
                Item lastChildItem = parent.getRightChildByIndex(parent.getRightChildItems().size() - 2);
                lastChildItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                for (Item rightItem : parent.getRightChildItems()){
                    rightItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                    rightItem.setY(rightItem.getY() - (item.getMeasuredHeight()/2 + spacing));
                }
                item.setY(lastChildItem.getY() + lastChildItem.getMeasuredHeight() + spacing);
            }
            else{
                item.setY(parent.getY());
            }

            Connector connector = new Connector(item, parent);
            rightItems.add(connector);
            item.addConnection(parent, ItemLocation.RIGHT, connector);

            if (dragAble)
                dragItem(item);

        }else if (location == ItemLocation.BOTTOM){

            this.addView(item);
            allItems.add(item);

            item.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            parent.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            item.setY(parent.getY() + (parent.getMeasuredHeight() + distance));

            parent.addBottomChild(item);

            if (parent.getBottomChildItems().size() > 1){
                Item lastChildItem = parent.getBottomChildByIndex(parent.getBottomChildItems().size() - 2);
                lastChildItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                for (Item bottomItem : parent.getBottomChildItems()){
                    bottomItem.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
                    bottomItem.setX(bottomItem.getX() - (item.getMeasuredWidth()/2 + spacing));
                }
                item.setX(lastChildItem.getX() + lastChildItem.getMeasuredWidth() + spacing);
            }else{
                item.setX(parent.getX());
            }

            Connector connector = new Connector(item, parent);
            bottomItems.add(connector);
            item.addConnection(parent, ItemLocation.BOTTOM, connector);

            if (dragAble)
                dragItem(item);
        }
        item.setOnClickListener(onClickListener);
        item.setOnTouchListener(null);
        item.setFocusable(false);
    }



    //Draw connections

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTopLines(canvas);
        drawLeftLines(canvas);
        drawRightLines(canvas);
        drawBottomLines(canvas);
        invalidate();
    }

    //* ***All of the draw methods are the work of JagarYousef, which is inside
    //    the mindmappinglibrary in the website https://github.com/JagarYousef/Mindo.***
    //    *
    // Draw connections (default)
    private void drawTopLines(Canvas canvas) {

        for (Connector connector : topItems){
            Item item = connector.getItem();
            Item parent = connector.getParent();
            int x1 = (int) (parent.getX() + parent.getWidth()/2);
            int y1 = (int) (parent.getY());
            int x2 = (int) (item.getX() + item.getWidth()/2);
            int y2 = (int) (item.getY() + item.getHeight());
            int radius = (int) (((item.getY() + item.getHeight()) - (parent.getY()))/4);
            drawCurvedArrowTop(x1, y1, x2, y2, radius, canvas,
                    item.getX() > parent.getX(), connector);
        }
    }
    private void drawLeftLines(Canvas canvas) {

        for (Connector connector : leftItems){
            Item item = connector.getItem();
            Item parent = connector.getParent();
            int x1 = (int) (parent.getX());
            int y1 = (int) (parent.getY() + parent.getHeight()/2);
            int x2 = (int) (item.getX() + item.getWidth());
            int y2 = (int) (item.getY() + item.getHeight()/2);
            int radius = (int) (((item.getX() + item.getWidth()) - (parent.getX()))/4);
            drawCurvedArrowLeft(x1, y1, x2, y2, radius, canvas,
                    (item.getY()+item.getHeight()) < (parent.getY() + parent.getHeight()),
                    connector);
        }
    }
    private void drawRightLines(Canvas canvas) {

        for (Connector connector : rightItems){
            Item item = connector.getItem();
            Item parent = connector.getParent();
            int x1 = (int) (parent.getX() + parent.getWidth());
            int y1 = (int) (parent.getY() + parent.getHeight()/2);
            int x2 = (int) (item.getX());
            int y2 = (int) (item.getY() + item.getHeight()/2);
            int radius = (int) (((parent.getX() + parent.getWidth()) - (item.getX()))/4);
            drawCurvedArrowRight(x1, y1, x2, y2, radius, canvas,
                    (item.getY()+item.getHeight()) < (parent.getY() + parent.getHeight()),
                    connector);
        }
    }
    private void drawBottomLines(Canvas canvas) {

        for (Connector connector : bottomItems){
            Item item = connector.getItem();
            Item parent = connector.getParent();
            int x1 = (int) (parent.getX() + parent.getWidth()/2);
            int y1 = (int) (parent.getY() + parent.getHeight());
            int x2 = (int) (item.getX() + item.getWidth()/2);
            int y2 = (int) (item.getY());
            int radius = (int) (((parent.getY() + parent.getHeight()) - (item.getY()))/4);
            drawCurvedArrowBottom(x1, y1, x2, y2, radius, canvas,
                    item.getX() > parent.getX(), connector);
        }
    }
    private void drawCurvedArrowTop(int x1, int y1, int x2, int y2, int curveRadius, Canvas canvas, boolean right,
                                     Connector connector) {

        int radius = connectionCircRadius;
        int arrowSize = connectionArrowSize;
        int lineWidth = connectionWidth;
        int argExt = connectionArgSize;
        String color = connectionColor;

        if (connector.getCircRadius() > 0)
            radius = connector.getCircRadius();
        else if (connector.getArrowSize() > 0)
            arrowSize = connector.getArrowSize();
        else if (connector.getWidth() > 0)
            lineWidth = connector.getWidth();
        else if (connector.getArgExt() > 0)
            argExt = connector.getArgExt();

        int y1_from_circ  = y1 - radius;
        int y2_to_trg = y2 + arrowSize + argExt;
        Paint paint  = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeCap(Paint.Cap.ROUND);

        final Path path = new Path();
        int midX            = x1 + ((x2 - x1) / 2);
        int midY            = y1_from_circ + ((y2_to_trg - y1_from_circ) / 2);
        float xDiff         = midX - x1;
        float yDiff         = midY - y1_from_circ;
        double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        float pointX, pointY;
        if (right){
            pointX        = (float) (midX + curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY + curveRadius * Math.sin(angleRadians));
        }else{
            pointX        = (float) (midX - curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY - curveRadius * Math.sin(angleRadians));
        }

        path.moveTo(x1, y1_from_circ);
        path.cubicTo(x1,y1_from_circ,pointX, pointY, x2, y2_to_trg);
        path.moveTo(x2, y2_to_trg);
        path.lineTo(x2, y2_to_trg - argExt);

        canvas.drawPath(path, paint);

        Paint paint2  = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(lineWidth);
        paint2.setColor(Color.parseColor(color));
        paint2.setStrokeCap(Paint.Cap.ROUND);

        Point point1 = new Point(x2-arrowSize/2, y2+arrowSize);
        Point point2 = new Point(x2+arrowSize/2,y2+arrowSize);
        Point point3 = new Point(x2, y2);

        Path path2 = new Path();
        path2.moveTo(x2, y2_to_trg);
        path2.lineTo(point1.x, point1.y);
        path2.lineTo(point2.x, point2.y);
        path2.lineTo(point3.x, point3.y);
        path2.lineTo(point1.x, point1.y);
        path2.close();
        canvas.drawPath(path2, paint2);

        canvas.drawCircle(x1, y1-radius, radius, paint2);

       if (argExt > 0){
            canvas.drawCircle(x2, y2_to_trg+radius, radius, paint2);
        }


    }
    private void drawCurvedArrowLeft(int x1, int y1, int x2, int y2, int curveRadius,
                                     Canvas canvas, boolean top, Connector connector) {

        int radius = connectionCircRadius;
        int arrowSize = connectionArrowSize;
        int lineWidth = connectionWidth;
        int argExt = connectionArgSize;
        String color = connectionColor;

        if (connector.getCircRadius() > 0)
            radius = connector.getCircRadius();
        else if (connector.getArrowSize() > 0)
            arrowSize = connector.getArrowSize();
        else if (connector.getWidth() > 0)
            lineWidth = connector.getWidth();
        else if (connector.getArgExt() > 0)
            argExt = connector.getArgExt();

        int x1_from_circ  = x1 - radius;
        int x2_to_trg = x2 + arrowSize + argExt;
        Paint paint  = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeCap(Paint.Cap.ROUND);

        final Path path = new Path();
        int midX            = x1_from_circ + ((x2_to_trg - x1_from_circ) / 2);
        int midY            = y1 + ((y2 - y1) / 2);
        float xDiff         = midX - x1_from_circ;
        float yDiff         = midY - y1;
        double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        float pointX, pointY;
        if (top){
            pointX        = (float) (midX + curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY + curveRadius * Math.sin(angleRadians));
        }else{
            pointX        = (float) (midX - curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY - curveRadius * Math.sin(angleRadians));
        }

        path.moveTo(x1_from_circ, y1);
        path.cubicTo(x1_from_circ,y1,pointX, pointY, x2_to_trg, y2);
        path.moveTo(x2_to_trg, y2);
        path.lineTo(x2_to_trg - argExt, y2);
        path.close();

        canvas.drawPath(path, paint);

        Paint paint2  = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(lineWidth);
        paint2.setColor(Color.parseColor(color));
        paint2.setStrokeCap(Paint.Cap.ROUND);

        Point point1 = new Point(x2+arrowSize, y2-arrowSize/2);
        Point point2 = new Point(x2+arrowSize,y2+arrowSize/2);
        Point point3 = new Point(x2, y2);

        path.moveTo(x2_to_trg,y2);
        path.lineTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        path.lineTo(point1.x, point1.y);
        path.close();

        Path path2 = new Path();
        path2.moveTo(x2_to_trg, y2);
        path2.lineTo(point1.x, point1.y);
        path2.lineTo(point2.x, point2.y);
        path2.lineTo(point3.x, point3.y);
        path2.lineTo(point1.x, point1.y);
        path2.close();

        canvas.drawPath(path2, paint2);

        canvas.drawCircle(x1-radius, y1, radius, paint2);

      if (argExt > 0){
            canvas.drawCircle(x2_to_trg, y2, radius, paint2);
        }



    }
    private void drawCurvedArrowRight(int x1, int y1, int x2, int y2, int curveRadius,
                                      Canvas canvas, boolean top, Connector connector) {

        int radius = connectionCircRadius;
        int arrowSize = connectionArrowSize;
        int lineWidth = connectionWidth;
        int argExt = connectionArgSize;
        String color = connectionColor;

        if (connector.getCircRadius() > 0)
            radius = connector.getCircRadius();
        else if (connector.getArrowSize() > 0)
            arrowSize = connector.getArrowSize();
        else if (connector.getWidth() > 0)
            lineWidth = connector.getWidth();
        else if (connector.getArgExt() > 0)
            argExt = connector.getArgExt();

        int x1_from_circ  = x1 + radius;
        int x2_to_trg = x2 - arrowSize - argExt;
        Paint paint  = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeCap(Paint.Cap.ROUND);

        final Path path = new Path();
        int midX            = x1_from_circ + ((x2_to_trg - x1_from_circ) / 2);
        int midY            = y1 + ((y2 - y1) / 2);
        float xDiff         = midX - x1_from_circ;
        float yDiff         = midY - y1;
        double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        float pointX, pointY;
        if (top){
            pointX        = (float) (midX - curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY - curveRadius * Math.sin(angleRadians));
        }else{
            pointX        = (float) (midX + curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY + curveRadius * Math.sin(angleRadians));
        }

        path.moveTo(x1_from_circ, y1);
        path.cubicTo(x1_from_circ,y1,pointX, pointY, x2_to_trg, y2);
        path.moveTo(x2_to_trg, y2);
        path.lineTo(x2_to_trg + argExt, y2);
        path.close();

        canvas.drawPath(path, paint);

        Paint paint2  = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(lineWidth);
        paint2.setColor(Color.parseColor(color));
        paint2.setStrokeCap(Paint.Cap.ROUND);

        Point point1 = new Point(x2-arrowSize, y2-arrowSize/2);
        Point point2 = new Point(x2-arrowSize,y2+arrowSize/2);
        Point point3 = new Point(x2, y2);

        path.moveTo(x2_to_trg,y2);
        path.lineTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        path.lineTo(point1.x, point1.y);
        path.close();

        Path path2 = new Path();
        path2.moveTo(x2_to_trg, y2);
        path2.lineTo(point1.x, point1.y);
        path2.lineTo(point2.x, point2.y);
        path2.lineTo(point3.x, point3.y);
        path2.lineTo(point1.x, point1.y);
        path2.close();

        canvas.drawPath(path2, paint2);

        canvas.drawCircle(x1+radius, y1, radius, paint2);

      if (argExt > 0){
            canvas.drawCircle(x2_to_trg, y2, radius, paint2);
        }



    }
    private void drawCurvedArrowBottom(int x1, int y1, int x2, int y2, int curveRadius,
                                       Canvas canvas, boolean right, Connector connector) {

        int radius = connectionCircRadius;
        int arrowSize = connectionArrowSize;
        int lineWidth = connectionWidth;
        int argExt = connectionArgSize;
        String color = connectionColor;

        if (connector.getCircRadius() > 0)
            radius = connector.getCircRadius();
        else if (connector.getArrowSize() > 0)
            arrowSize = connector.getArrowSize();
        else if (connector.getWidth() > 0)
            lineWidth = connector.getWidth();
        else if (connector.getArgExt() > 0)
            argExt = connector.getArgExt();

        int y1_from_circ  = y1 + radius;
        int y2_to_trg = y2 - arrowSize - argExt;
        Paint paint  = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeCap(Paint.Cap.ROUND);

        final Path path = new Path();
        int midX            = x1 + ((x2 - x1) / 2);
        int midY            = y1_from_circ + ((y2_to_trg - y1_from_circ) / 2);
        float xDiff         = midX - x1;
        float yDiff         = midY - y1_from_circ;
        double angle        = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        float pointX, pointY;
        if (right){
            pointX        = (float) (midX - curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY - curveRadius * Math.sin(angleRadians));
        }else{
            pointX        = (float) (midX + curveRadius * Math.cos(angleRadians));
            pointY        = (float) (midY + curveRadius * Math.sin(angleRadians));
        }

        path.moveTo(x1, y1_from_circ);
        path.cubicTo(x1,y1_from_circ,pointX, pointY, x2, y2_to_trg);
        path.moveTo(x2, y2_to_trg);
        path.lineTo(x2, y2);
        path.close();
        canvas.drawPath(path, paint);

        Paint paint2  = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(lineWidth);
        paint2.setColor(Color.parseColor(color));
        paint2.setStrokeCap(Paint.Cap.ROUND);

        Point point1 = new Point(x2-arrowSize/2, y2-arrowSize);
        Point point2 = new Point(x2+arrowSize/2,y2-arrowSize);
        Point point3 = new Point(x2, y2);

        Path path2 = new Path();
        path2.moveTo(x2, y2_to_trg);
        path2.lineTo(point1.x, point1.y);
        path2.lineTo(point2.x, point2.y);
        path2.lineTo(point3.x, point3.y);
        path2.lineTo(point1.x, point1.y);
        path2.close();
        canvas.drawPath(path2, paint2);

        canvas.drawCircle(x1, y1+radius, radius, paint2);

        if (argExt > 0){
            canvas.drawCircle(x2, y2_to_trg, radius, paint2);
        }

    }

    //Setting the listener for the view's items

    public void  setOnItemClicked(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        storeOnItemClicked = onClickListener;
    }


    public void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this.getContext(),v);
        popupMenu.getMenuInflater().inflate(R.menu.mind_map_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.left){
                    addItem(new Item(MindMapView.this.getContext()), (Item) v, 1, 0, ItemLocation.LEFT,
                    true);
                }
                if(item.getItemId() == R.id.right){
                    addItem(new Item(MindMapView.this.getContext()), (Item) v, 1, 0, ItemLocation.RIGHT,
                            true);
                }
                if(item.getItemId() == R.id.top){
                    addItem(new Item(MindMapView.this.getContext()), (Item) v, 1, 0, ItemLocation.TOP,
                            true);
                }
                if(item.getItemId() == R.id.bottom){
                    addItem(new Item(MindMapView.this.getContext()), (Item) v, 1, 0, ItemLocation.BOTTOM,
                            true);
                }
                if(item.getItemId() == R.id.delete){
                    Item it = (Item) v;
                    ArrayList<Connector> cons = it.getConnectors();
                    for(Connector c : cons){
                        int loc = c.getLocation();
                        if(loc == ItemLocation.TOP){
                            topItems.remove(c);
                        }
                        else if(loc == ItemLocation.BOTTOM){
                            bottomItems.remove(c);
                        }
                        else if(loc == ItemLocation.LEFT){
                            leftItems.remove(c);
                        }
                        else if(loc == ItemLocation.RIGHT){
                            rightItems.remove(c);
                        }
                    }
                    ((ViewGroup)it.getParent()).removeView(it);
                }
                invalidate();
                return true;
            }
        });
        popupMenu.show();

    }


}
