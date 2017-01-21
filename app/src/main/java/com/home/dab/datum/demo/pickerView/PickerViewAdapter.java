package com.home.dab.datum.demo.pickerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                if (mSlidingDistance > mRecyclerViewHeight / 10) {
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                if (a) {
//                    mSlidingDistance = mSlidingDistanceTotal % mItemHeight;
//                    a = false;
//                }
                mSlidingDistance = mSlidingDistanceTotal % mItemHeight;
                if (mSlidingDistance > mItemHeight / 2 + 5) {
                    mSlidingDistance = -(mItemHeight - mSlidingDistance);
                    recyclerView.smoothScrollBy(0, mSlidingDistance, new LinearInterpolator());
//                    Log.e(TAG, "onScrollStateChanged: 下滑" + (mItemHeight - slidingDistance));
                } else if (mSlidingDistance < mItemHeight / 2 - 5){
                    recyclerView.smoothScrollBy(0, mSlidingDistance, new LinearInterpolator());
//                    Log.e(TAG, "onScrollStateChanged: 上滑" + slidingDistance);
                }
//                Log.e(TAG, "onScrollStateChanged: " + slidingDistance);
//                recyclerView.smoothScrollBy(0, mSlidingDistance, new LinearInterpolator());
                a = false;
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            Log.e(TAG, "onScrolled: " + mFirstVisibleItemPosition);
            //180
            Log.e(TAG, "onScrolled: " );
            mSlidingDistanceTotal += dy;
//            mSlidingDistance = mSlidingDistance %(mRecyclerViewHeight / mEntryCount);
//            Log.e(TAG, "onScrolled: " + (mSlidingDistanceTotal));
//            Log.e(TAG, "onScrolled: " + mSlidingDistance + "****" + recyclerView.getChildCount());

            int elected = mEntryCount / 2;
//            for (int i = 0; i < recyclerView.getChildCount(); i++) {
//                if (i < elected) {
//                    TextView childAt = (TextView) recyclerView.getChildAt(i);
//                    childAt.setRotationX(60);
//                } else if (i > elected) {
//                    TextView childAt = (TextView) recyclerView.getChildAt(i);
//                    childAt.setRotationX(300);
//                } else {
//                    TextView childAt = (TextView) recyclerView.getChildAt(i);
//                    childAt.setRotationX(0);
//                }

//            }
        }
    };

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
