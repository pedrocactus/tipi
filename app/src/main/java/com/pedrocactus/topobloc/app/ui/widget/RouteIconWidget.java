package com.pedrocactus.topobloc.app.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;

/**
 * Created by pierrecastex on 13/04/2015.
 */
public class RouteIconWidget extends FrameLayout {
    public static int SMALL=0;
    public static int MEDIUM=1;
    public static int BIG=2;

    public RouteIconWidget(Context context, String text , int resColor, int size) {
        super(context);
        inflate(text,resColor,size);
    }

    public RouteIconWidget(Context context,String text ,  AttributeSet attrs, int resColor, int size) {
        super(context, attrs);
        inflate(text,resColor,size);
    }

    public RouteIconWidget(Context context,String text ,  AttributeSet attrs, int defStyleAttr, int resColor, int size) {
        super(context, attrs, defStyleAttr);
        inflate(text,resColor,size);
    }

    public RouteIconWidget(Context context,String text ,  AttributeSet attrs, int defStyleAttr, int defStyleRes, int resColor, int size) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(text,resColor,size);
    }

    private void inflate(String text, int resColor, int size){
        inflate(getContext(), R.layout.route_icon_widget, this);
        int dimension=0;
        if(size == SMALL){
            dimension = 8;
        }else if(size == MEDIUM){
            dimension = 20;
        }else if(size == BIG){
            dimension = 20;
        }

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dimension , getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dimension, getResources().getDisplayMetrics());
        this.setLayoutParams(new ViewGroup.LayoutParams(
                height,
                width));
        TextView textView = (TextView) findViewById(R.id.routeNumber);
        textView.setText(text);
            ((GradientDrawable)textView.getBackground()).setColor(
                    getResources().getColor(resColor));
        textView.setText(text);
        if(size == BIG){
            ((GradientDrawable)textView.getBackground()).setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2 , getResources().getDisplayMetrics()), getResources().getColor(R.color.black));
        }else if (size == SMALL){
            textView.setTextSize(5);
            ((GradientDrawable)textView.getBackground()).setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1 , getResources().getDisplayMetrics()), getResources().getColor(R.color.black));

        }

        ((GradientDrawable)textView.getBackground()).setSize(height,width);
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
    }
}
