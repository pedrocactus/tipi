package com.pedrocactus.topobloc.app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pedrocactus.topobloc.app.R;

/**
 * Created by pierrecastex on 13/04/2015.
 */
public class RouteIconWidget extends FrameLayout {
    public RouteIconWidget(Context context, int resColor) {
        super(context);
        inflate(resColor);
    }

    public RouteIconWidget(Context context, AttributeSet attrs, int resColor) {
        super(context, attrs);
        inflate(resColor);
    }

    public RouteIconWidget(Context context, AttributeSet attrs, int defStyleAttr, int resColor) {
        super(context, attrs, defStyleAttr);
        inflate(resColor);
    }

    public RouteIconWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int resColor) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(resColor);
    }

    private void inflate(int resColor){
        inflate(getContext(), R.layout.route_icon_widget, this);
        TextView textView = (TextView) findViewById(R.id.routeNumber);
        textView.setTextColor(resColor);

    }
}
