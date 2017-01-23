package com.home.dab.datum.demo.pickerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DAB on 2017/1/21 15:50.
 */

public class PickerViewAdapter extends RecyclerView.Adapter<PickerViewAdapter.ViewHolder> {
    List<String> mStringList;
    private final int mRecyclerViewHeight;
    private final int mRecyclerViewWidth;
    private final int mRecyclerVieweCentreY;
    private final LinearLayoutManager mLinearLayoutManager;
    private int mFirstVisibleItemPosition;
    private int mSlidingDistanceTotal;
    private int mEntryCount = 5;
    private int mThreshold;
    private int mItemHeight;
    private int mSlidingDistance;
    private float startAngle = 80;
    private float endAngle = -80;
    private float maxSize = 36;
    private float minSize = 18;
    public PickerViewAdapter(RecyclerView recyclerView, List<String> stringList) {
//        stringList.add(0, "");
//        stringList.add(0, "");
//        stringList.add(stringList.size(), "");
//        stringList.add(stringList.size(), "");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            mLinearLayoutManager = (LinearLayoutManager) layoutManager;
        } else {
            throw new RuntimeException("只能使用LinearLayoutManager作为布局管理器");
        }
        recyclerView.addOnScrollListener(mOnScrollListener);
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        mRecyclerViewHeight = layoutParams.height;
        mRecyclerViewWidth = layoutParams.width;
        mRecyclerVieweCentreY = mRecyclerViewHeight / 2;
        if (mRecyclerViewHeight < 0) {
            throw new RuntimeException("高度需要指定具体大小");
        }
//        mRecyclerViewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        mStringList = stringList;
    }

    @Override
    public PickerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getTextView(parent.getContext()));
    }

    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(36);
        mItemHeight = mRecyclerViewHeight / mEntryCount;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mRecyclerViewWidth, mItemHeight);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    public void onBindViewHolder(PickerViewAdapter.ViewHolder holder, int position) {
        mFirstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        holder.mTextView.setText(mStringList.get(position));
    }

    boolean a;
    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            Log.e(TAG, "onScrollStateChanged: " + newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                a = true;
            }
//            if (!a) return;
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
////                if (mSlidingDistance > mRecyclerViewHeight / 10) {
//                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
////                if (a) {
////                    mSlidingDistance = mSlidingDistanceTotal % mItemHeight;
////                    a = false;
////                }
                mSlidingDistance = mSlidingDistanceTotal % mItemHeight;
                if (mSlidingDistance > mItemHeight / 2) {
                    mSlidingDistance = (mItemHeight - mSlidingDistance);

                } else if (mSlidingDistance < mItemHeight / 2) {
                    mSlidingDistance = -mSlidingDistance;
                }
                recyclerView.smoothScrollBy(0, mSlidingDistance, new AccelerateDecelerateInterpolator());
//                recyclerView.smoothScrollBy(0, mSlidingDistance, new LinearInterpolator());
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            Log.e(TAG, "onScrolled: " + mFirstVisibleItemPosition);
            //180
//            Log.e(TAG, "onScrolled: " );
            mSlidingDistanceTotal += dy;
//            mSlidingDistance = mSlidingDistance %(mRecyclerViewHeight / mEntryCount);
//            Log.e(TAG, "onScrolled: " + (mSlidingDistanceTotal));
//            Log.e(TAG, "onScrolled: " + mSlidingDistance + "****" + recyclerView.getChildCount());

//            TextView childAt = (TextView) recyclerView.getChildAt(0);
//            TextView childAt1 = (TextView) recyclerView.getChildAt(1);
//            TextView childAt2 = (TextView) recyclerView.getChildAt(2);
//            TextView childAt3 = (TextView) recyclerView.getChildAt(3);
//            TextView childAt4 = (TextView) recyclerView.getChildAt(4);
//            TextView childAt5 = (TextView) recyclerView.getChildAt(5);
//            childAt.setRotationX(80+getChangeScale());
//            childAt1.setRotationX(45+getChangeScale());
//            childAt2.setRotationX(0+getChangeScale());
//            childAt3.setRotationX(-45+getChangeScale());
//            childAt4.setRotationX(-80+getChangeScale());
//            if (childAt5 != null) {
//                childAt5.setRotationX(-125+getChangeScale());
//            }


            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                TextView childAt = (TextView) recyclerView.getChildAt(i);
                childAt.setRotationX(getRotateAngle(i));
                if ( i == mEntryCount / 2) {
                    childAt.setTextSize(28);
                } else {
                    childAt.setTextSize(18);
                }
//                childAt.setTextSize(getTextSize(i));
                Log.e(TAG, i + "onScrolled: " + getTextSize(i));
            }
        }
    };

    /**
     * 获取旋转的角度
     *
     * @param position
     * @return
     */
    private float getRotateAngle(int position) {
        float totalAngle = endAngle - startAngle;
        float eachAngle = totalAngle / (mEntryCount - 1);
        return getChangeScale() * Math.abs(eachAngle) + startAngle + (eachAngle * position);
    }

    private float getTextSize(int position) {
        float totalSize = maxSize - minSize;
        float eachSize = totalSize / (mEntryCount - 1);
        int elected = mEntryCount / 2;
        if (position == elected) {
            return maxSize;
        }
        if (position < elected) {
            return getChangeScale() * Math.abs(eachSize) + minSize + (eachSize * position);
        } else {
            return maxSize - getChangeScale() * Math.abs(eachSize) - (eachSize * position);
        }
//        return getChangeScale() * Math.abs(eachSize);
    }

    /**
     * 获取比例
     *
     * @return
     */
    private float getChangeScale() {
        mSlidingDistance = mSlidingDistanceTotal % mItemHeight;
        return (float) (mSlidingDistance) / mItemHeight;
    }


    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    /**
     * 获取view的Y中心坐标
     *
     * @param view
     * @return
     */
    private int getViewCentreY(View view) {
        return (view.getBottom() - view.getTop()) / 2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
