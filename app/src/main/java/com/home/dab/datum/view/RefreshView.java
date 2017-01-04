package com.home.dab.datum.view;

import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by DAB on 2016/12/5 17:05.
 * 下拉刷新的
 */

public class RefreshView extends ViewGroup {

    private static final String TAG = "RefreshView";
    //头部的高度
    protected float mHeadHeight, mHeadWidth;

    //底部高度
    private float mFootHeight, mFootWidth;

    private float mContentHeight, mContentWidth;

    private float mStartRefreshDistance;//需要下拉的距离才触发刷新

    private ViewDragHelper mViewDragHelper;

    private static final int PULL_DOWN_REFRESH = 1;//标志当前进入的刷新模式
    private static final int PULL_UP_LOAD = 2;
    private int state = PULL_DOWN_REFRESH;
    //触摸获得Y的位置
    private float mTouchY;
    //触摸获得X的位置(为防止滑动冲突而设置)
    private float mTouchX;

    private View mContentView, mHeadView, mFootView;
    private List<View> mConflictViews;

    public void setConflictView(@NonNull View conflictView) {
        if (mConflictViews == null) {
            mConflictViews = new ArrayList<>();
        }
        mConflictViews.add(conflictView);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putFloat("mHeadHeight",mHeadHeight);
        bundle.putFloat("mHeadWidth",mHeadWidth);
        bundle.putFloat("mContentHeight",mContentHeight);
        bundle.putFloat("mContentWidth",mContentWidth);
        bundle.putFloat("mStartRefreshDistance",mStartRefreshDistance);
        bundle.putInt("state",state);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Bundle bundle = (Bundle) state;
        mHeadHeight=bundle.getFloat("mHeadHeight",mHeadHeight);
        mHeadWidth=bundle.getFloat("mHeadWidth",mHeadWidth);
        mContentHeight=bundle.getFloat("mContentHeight",mContentHeight);
        mContentWidth=bundle.getFloat("mContentWidth",mContentWidth);
        mStartRefreshDistance=bundle.getFloat("mStartRefreshDistance",mStartRefreshDistance);
    }

    public RefreshView(Context context) {
        this(context, null, 0);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        Log.e(TAG, "init: " + childCount);
//        if (childCount < 1 || childCount > 3) {
//            throw new RuntimeException("里面只能含有一到三个子View!");
//        }
        if (childCount != 2) {
            throw new RuntimeException("里面只能含有2个子View!");
        }
//        int childCount = getChildCount();
        switch (childCount) {
            case 1:
                mContentView = getChildAt(0);
                break;
            case 2:
                mHeadView = getChildAt(0);
                mContentView = getChildAt(1);
                break;
            case 3:
                mHeadView = getChildAt(0);
                mContentView = getChildAt(1);
                mFootView = getChildAt(2);
                break;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            Log.e(TAG, "measureWidth is " + v.getMeasuredWidth() + "measureHeight is " + v.getMeasuredHeight());
            int widthSpec = 0;
            int heightSpec = 0;
            LayoutParams params = v.getLayoutParams();
            if (params.width > 0) {
                widthSpec = MeasureSpec.makeMeasureSpec(params.width, MeasureSpec.EXACTLY);
            } else if (params.width == MATCH_PARENT) {
                widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
            } else if (params.width == WRAP_CONTENT) {
                widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.AT_MOST);
            }

            if (params.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
            } else if (params.height == MATCH_PARENT) {
                heightSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
            } else if (params.height == WRAP_CONTENT) {
                heightSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.AT_MOST);
            }
            v.measure(widthSpec, heightSpec);

        }


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mHeadView != null) {
            mHeadHeight = mHeadView.getMeasuredHeight();
            mHeadWidth = mHeadView.getMeasuredWidth();
            if (mStartRefreshDistance == 0) {
                mStartRefreshDistance = mHeadHeight;
            }
        }
        if (mFootView != null) {
            mFootHeight = mFootView.getMeasuredHeight();
            mFootWidth = mFootView.getMeasuredWidth();
        }
        if (mContentView != null) {
            mContentHeight = mContentView.getMeasuredHeight();
            mContentWidth = mContentView.getMeasuredWidth();
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mHeadView.layout(0, (int) (-mHeadHeight), (int) mHeadWidth, 0);
        mContentView.layout(0, 0, (int) mContentWidth, (int) mContentHeight);
    }

    boolean isConflict;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        isConflict = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = event.getY();
                mTouchX = event.getX();
                event.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
                event.getRawY(); // 获取相对于屏幕左上角的 y 坐标值
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否需要拦截这个事件
                return isInterceptTouchEvent(event);
        }
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    private boolean isInterceptTouchEvent(MotionEvent event) {
        //获取滑动时的Y值变化
        float y = event.getY() - mTouchY;
        //如果是向上滑,就交给ViewDragHelper的shouldInterceptTouchEvent去判断
        if (y < 0) return mViewDragHelper.shouldInterceptTouchEvent(event);
        //如果没有设置冲突的view,就根据mContentView来判断是否需要拦截
        if (mConflictViews == null && canChildScrollUp(mContentView)) {
            return false;
        } else
            //如果设置了冲突的view
            if (mConflictViews != null) {
                //遍历冲突的view集合
                for (int i = 0; i < mConflictViews.size(); i++) {
                    View view = mConflictViews.get(i);
                    //判断这个view是否被触摸
                    boolean touched = isTouched(view, event.getRawX(), event.getRawY());
                    //如果被触摸了,则判断这个view还能不能向上滑动,如果能则不拦截事件
                    if (touched) {
                        if (canChildScrollUp(view)) {
                            return false;
                        }
                    }
                }
            }
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return (child == mContentView);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (state != PULL_UP_LOAD) {
                //回调出去当前拉的距离
                if (mOnPullDownDistanceChange != null) {
                    mOnPullDownDistanceChange.onPullDownDistanceChange((int) (mStartRefreshDistance*1.5), top);
                }
            }
            if (top < 0) top = 0;//禁止往上拉(只能往下拉)
//            if (top > mHeadHeight * 2) top = (int) (mHeadHeight * 2);
            return top;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mContentView == child ? child.getHeight() : 0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //修改头布局的位置
            mHeadView.layout(mHeadView.getLeft() + dx, mHeadView.getTop() + dy, mHeadView.getRight() + dx, mHeadView.getBottom() + dy);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            //判断是否应该刷新
            if (mHeadView.getTop() > mStartRefreshDistance / 2) {
                state = PULL_UP_LOAD;
                mViewDragHelper.smoothSlideViewTo(mContentView, 0, (int) mHeadHeight);
                ViewCompat.postInvalidateOnAnimation(RefreshView.this);
            } else close();
        }
    };

    /**
     * 回到初始的位置
     */
    public void close() {
        state = PULL_DOWN_REFRESH;
        mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(RefreshView.this);
    }

    @Override
    public void computeScroll() {
        //判断是否滚动完成,没有则继续
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(RefreshView.this);
        } else {
            //滚动完成,来根据状态判断是否需要需要回调刷新
            if (state == PULL_UP_LOAD) {
                if (mOnRefreshing != null) {
                    mOnRefreshing.onRefreshing();
                }
            }
        }
    }

    private OnPullDownDistanceChange mOnPullDownDistanceChange;

    public void setOnPullDownDistanceChange(OnPullDownDistanceChange onPullDownDistanceChange) {
        mOnPullDownDistanceChange = onPullDownDistanceChange;
    }

    private OnRefreshing mOnRefreshing;

    public void setOnRefreshing(OnRefreshing onRefreshing) {
        mOnRefreshing = onRefreshing;
    }

    /**
     * 下拉距离回调
     */
    public interface OnPullDownDistanceChange {
        /**
         * @param startRefreshDistance 超过这个距离开始刷新
         * @param distance             当前的距离
         */
        void onPullDownDistanceChange(int startRefreshDistance, int distance);
    }

    /**
     * 刷新的回调
     */
    public interface OnRefreshing {
        void onRefreshing();
    }


    /**
     * 用来判断是否可以下拉(解决点击冲突用)
     */
    public static boolean canChildScrollUp(View mChildView) {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    /**
     * 判断 View 是否被触摸。
     */
    public static boolean isTouched(View view, float rawX, float rawY) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        RectF rectF = new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
        return rectF.contains(rawX, rawY);
    }
}

