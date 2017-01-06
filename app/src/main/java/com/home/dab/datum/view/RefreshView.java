package com.home.dab.datum.view;

import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
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
    private float mStartLoadDistance;//需要上拉的距离才触发加载
    private int mTopMaxDistance;//上拉最大距离
    private int mDownMaxDistance;//下拉最大距离

    private ViewDragHelper mViewDragHelper;

    private static final int FLAG_TOP_MAX_DISTANCE = 1;//标志设置了最大下拉最大距离
    private static final int FLAG_DOWN_MAX_DISTANCE = 2;//标志设置了最大的上拉距离

    public static final int PULL_LEISURE_REFRESH = 0;
    public static final int PULL_TOP_LOAD = 1;//标志当前进入的x下拉刷新模式
    public static final int PULL_DOWN_LOAD = 2;//标志当前进入的上拉加载模式

    public int state;
    //触摸获得Y的位置
    private float mTouchY;
    private long flag;
    private View mContentView, mHeadView, mFootView;
    private List<View> mConflictViews;
    private OnRefreshViewListener mRefreshViewListener;


    public void setRefreshViewListener(OnRefreshViewListener refreshViewListener) {
        mRefreshViewListener = refreshViewListener;
    }

    public void setConflictView(@NonNull View conflictView) {
        if (mConflictViews == null) {
            mConflictViews = new ArrayList<>();
        }
        mConflictViews.add(conflictView);
    }

    public void setDownMaxDistance(int downMaxDistancePX) {
        flag = flag + FLAG_DOWN_MAX_DISTANCE;
        mDownMaxDistance = downMaxDistancePX;
    }

    public void setTopMaxDistance(int topMaxDistancePX) {
        flag = flag + FLAG_TOP_MAX_DISTANCE;
        mTopMaxDistance = topMaxDistancePX;
    }

//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Bundle bundle = new Bundle();
//        bundle.putFloat("mHeadHeight",mHeadHeight);
//        bundle.putFloat("mHeadWidth",mHeadWidth);
//        bundle.putFloat("mContentHeight",mContentHeight);
//        bundle.putFloat("mContentWidth",mContentWidth);
//        bundle.putFloat("mStartRefreshDistance",mStartRefreshDistance);
//        bundle.putInt("state",state);
//        return bundle;
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        super.onRestoreInstanceState(state);
//        Bundle bundle = (Bundle) state;
//        mHeadHeight=bundle.getFloat("mHeadHeight",mHeadHeight);
//        mHeadWidth=bundle.getFloat("mHeadWidth",mHeadWidth);
//        mContentHeight=bundle.getFloat("mContentHeight",mContentHeight);
//        mContentWidth=bundle.getFloat("mContentWidth",mContentWidth);
//        mStartRefreshDistance=bundle.getFloat("mStartRefreshDistance",mStartRefreshDistance);
//    }

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
        if (childCount != 2 && childCount != 3) {
            throw new RuntimeException("里面只能含有2到3个子View!");
        }
//        int childCount = getChildCount();
        switch (childCount) {
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
                mStartRefreshDistance = mHeadHeight / 2;
            }
        }
        if (mFootView != null) {
            mFootHeight = mFootView.getMeasuredHeight();
            mFootWidth = mFootView.getMeasuredWidth();
            if (mStartLoadDistance == 0) {
                mStartLoadDistance = mFootHeight / 2;
            }
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
        if (mFootView != null)
            mFootView.layout(0, (int) mContentHeight, (int) mFootWidth, (int) (mContentHeight + mFootHeight));
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = event.getY();

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
        //如果是手指向上滑,就交给ViewDragHelper的shouldInterceptTouchEvent去判断
//        if (y < 0) return mViewDragHelper.shouldInterceptTouchEvent(event);



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
//                    if (touched) {
//                        if (canChildScrollUp(view)) {
//                            return false;
//                        }
//                    }
                    if (touched) {
                        if (y < 0) {
                            if (canChildScrollDown(view)) {
                                return false;
                            }
                        } else {
                            if (canChildScrollUp(view)) {
                                return false;
                            }
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

//            if (mTopMaxDistance != 0 && top > mTopMaxDistance) top = mTopMaxDistance;
//            if (mDownMaxDistance != 0 && -top > mTopMaxDistance) top = -mTopMaxDistance;
            mTopMaxDistance = ((flag & FLAG_TOP_MAX_DISTANCE) == FLAG_TOP_MAX_DISTANCE) ? mTopMaxDistance : (int) mHeadHeight;
            if (top > mTopMaxDistance) top = mTopMaxDistance;
            if (mFootView != null) {
                mDownMaxDistance = ((flag & FLAG_DOWN_MAX_DISTANCE) == FLAG_DOWN_MAX_DISTANCE) ? mDownMaxDistance : (int) mFootHeight;
                if (-top > mDownMaxDistance) top = -mDownMaxDistance;
            } else if (top < 0) top = 0;
            if (state == PULL_LEISURE_REFRESH) {
                //回调出去当前拉的距离
                if (mRefreshViewListener != null) {
                    mRefreshViewListener.onPullDownDistanceChange((top < 0) ? PULL_DOWN_LOAD : PULL_TOP_LOAD, (int) (mStartRefreshDistance), top);
                }
            }
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
            if (mFootView != null) {
                //修改脚布局的位置
                mFootView.layout(mFootView.getLeft() + dx, mFootView.getTop() + dy, mFootView.getRight() + dx, mFootView.getBottom() + dy);
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //判断是否应该刷新
            if (mContentView.getTop() > mStartRefreshDistance) {
                state = PULL_TOP_LOAD;
                mViewDragHelper.smoothSlideViewTo(mContentView, 0, mContentView.getTop());
                ViewCompat.postInvalidateOnAnimation(RefreshView.this);
            } else if (mFootView != null) {
                if (-mContentView.getTop() > mStartLoadDistance) {
                    state = PULL_DOWN_LOAD;
                    mViewDragHelper.smoothSlideViewTo(mContentView, 0, mContentView.getTop());
                    ViewCompat.postInvalidateOnAnimation(RefreshView.this);
                } else close();
            } else close();
        }
    };


    /**
     * 回到初始的位置
     */
    public void close() {
        state = PULL_LEISURE_REFRESH;
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
            if (state != PULL_LEISURE_REFRESH) {
                if (mRefreshViewListener != null) {
                    mRefreshViewListener.onRefreshing(state);
                }
            }
        }
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
     * 用来判断是否可以下拉(解决点击冲突用)
     */
    public static boolean canChildScrollDown(View mChildView) {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() || absListView.getChildAt(absListView.getChildCount() - 1)
                        .getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(mChildView, 1) || mChildView.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, 1);
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

    /**
     * 下拉距离回调
     */
    public interface OnRefreshViewListener {
        /**
         * @param refreshFlag          上拉或者下拉的标志
         * @param startRefreshDistance 超过这个距离开始刷新
         * @param distance             当前的距离
         */
        void onPullDownDistanceChange(int refreshFlag, int startRefreshDistance, int distance);

        /**
         * 刷新的回调
         *
         * @param refreshFlag 刷新或者加载的标志
         */
        void onRefreshing(int refreshFlag);
    }

}

