package com.home.dab.datum.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by DAB on 2017/1/4 09:38.
 */

public class SlideButton extends ViewGroup {

    private View mInitialView;//初始化显示的view
    private View mLeftView, mRightView;//左边和右边View
    private OnContentClickListener mOnContentClickListener;
    private int mLeftViewWidth, mLeftViewHeight;
    private int mInitialViewWidth, mInitialViewHeight;
    private boolean isOpen; // 是否是打开的状态

    public void setOnContentClickListener(OnContentClickListener onContentClickListener) {
        mOnContentClickListener = onContentClickListener;
    }

    public SlideButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        if (childCount != 3) {
            throw new RuntimeException("子布局的个数只能等于3个");
        } else {
            mInitialView = getChildAt(0);
            mLeftView = getChildAt(1);
            mRightView = getChildAt(2);

            if (mOnContentClickListener != null) {
                mLeftView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnContentClickListener.onLeftClick(v);
                        close();
                    }
                });
                mRightView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnContentClickListener.onRightClick(v);
                        close();
                    }
                });
                mInitialView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        open();
                    }
                });
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeftViewWidth = mLeftView.getMeasuredWidth();
        mLeftViewHeight = mLeftView.getMeasuredHeight();
        mInitialViewWidth = mInitialView.getMeasuredWidth();
        mInitialViewHeight = mInitialView.getMeasuredHeight();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mInitialView.layout((r - mInitialViewWidth) / 2, (b - mInitialViewHeight) / 2, (r - mInitialViewWidth) / 2 + mInitialViewWidth, (b - mInitialViewHeight) / 2 + mInitialViewHeight);
        mLeftView.layout(0, 0, mLeftViewWidth, mLeftViewHeight);
        mRightView.layout(0, 0, mLeftViewWidth, mLeftViewHeight);
    }

    private void animation(boolean open) {

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator initialViewScaleY = ObjectAnimator.ofFloat(mInitialView, "scaleY", open ? 1f : 0f, open ? 0f : 1f);
        ObjectAnimator initialViewScaleX = ObjectAnimator.ofFloat(mInitialView, "scaleX", open ? 1f : 0f, open ? 0f : 1f);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLeftView, "scaleY", open ? 0f : 1f, open ? 1f : 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLeftView, "scaleX", open ? 0f : 1f, open ? 1f : 0f);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(mLeftView, "translationX"
                , open ? (this.getMeasuredWidth() - mLeftViewWidth) / 2 : (this.getMeasuredWidth() - 84) / 2 - mLeftViewWidth
                , open ? (this.getMeasuredWidth() - 84) / 2 - mLeftViewWidth : (this.getMeasuredWidth() - mLeftViewWidth) / 2);


        ObjectAnimator PictureScaleY = ObjectAnimator.ofFloat(mRightView, "scaleY", open ? 0f : 1f, open ? 1f : 0f);
        ObjectAnimator PictureScaleX = ObjectAnimator.ofFloat(mRightView, "scaleX", open ? 0f : 1f, open ? 1f : 0f);
        ObjectAnimator PictureMoveIn = ObjectAnimator.ofFloat(mRightView, "translationX"
                , open ? (this.getMeasuredWidth() - mLeftViewWidth) / 2 : (this.getMeasuredWidth() + 84) / 2
                , open ? (this.getMeasuredWidth() + 84) / 2 : (this.getMeasuredWidth() - mLeftViewWidth) / 2);
        animatorSet.play(initialViewScaleY).with(initialViewScaleX)
                .with(scaleY).with(scaleX).with(moveIn)
                .with(PictureScaleY).with(PictureScaleX).with(PictureMoveIn)
        ;
        animatorSet.setDuration(300);
        animatorSet.start();
        mInitialView.setEnabled(!open);
    }

    public void open() {
        mLeftView.setVisibility(View.VISIBLE);
        mRightView.setVisibility(View.VISIBLE);
        if (!isOpen) {
            animation(true);
            isOpen = true;
        }
    }

    public void close() {
        if (isOpen) {
            animation(false);
            isOpen = false;
        }

    }


    public interface OnContentClickListener {
        void onRightClick(View view);
        void onLeftClick(View view);

    }
}
