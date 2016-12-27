package com.home.dab.datum.demo.md.coordinatorLayout.recycler;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by DAB on 2016/12/27 09:31.
 */

public class HeadBehavior<T extends View> extends CoordinatorLayout.Behavior<T>{
    private int height;
    public HeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        height = display.heightPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, T child, View dependency) {
        return (dependency instanceof RecyclerView);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, T child, View dependency) {
        int top = dependency.getTop();
        int left = dependency.getLeft();
        int x = left;
        int y = height-top-child.getHeight();
        setPosition(child, x, y);
        return true;
    }

    private void setPosition(T child, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) child.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        child.setLayoutParams(layoutParams);
    }
}
