package com.home.dab.datum.demo.recyclerDemo.picker;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DAB on 2017/1/19 10:41.
 */

public class PickerRecyclerAdapter extends RecyclerView.Adapter<PickerRecyclerAdapter.ViewHolder> {
    private List<String> mStringList;
    private int recyclerViewMeasuredWidth, recyclerViewMeasuredHeight;
    private TextView mTextView;

    public void setStringList(List<String> stringList) {
        mStringList = stringList;
        super.notifyDataSetChanged();
    }

    public PickerRecyclerAdapter(int recyclerViewMeasuredWidth, int recyclerViewMeasuredHeight) {
        this.recyclerViewMeasuredWidth = recyclerViewMeasuredWidth;
        this.recyclerViewMeasuredHeight = recyclerViewMeasuredHeight;

    }

    public PickerRecyclerAdapter(RecyclerView recyclerView, List<String> stringList) {
        mStringList = stringList;
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                recyclerViewMeasuredWidth = recyclerView.getMeasuredWidth();
                recyclerViewMeasuredHeight = recyclerView.getMeasuredHeight();
//                PickerRecyclerAdapter.this.super.notifyDataSetChanged();
                Log.e(TAG, "onGlobalLayout: " + recyclerViewMeasuredWidth + "***" + recyclerViewMeasuredHeight);
                setStringList(mStringList);
                recyclerView.scrollToPosition(0);
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LinearLayout layout = new LinearLayout(parent.getContext());
//        layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        if (mTextView == null) {
        mTextView = new TextView(parent.getContext());
        mTextView.setLayoutParams(new LinearLayoutCompat.LayoutParams(recyclerViewMeasuredWidth, recyclerViewMeasuredHeight / 5));
//        }
//        layout.addView(textView);
        return new ViewHolder(mTextView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
//            mTextView = (TextView) ((LinearLayout) itemView).getChildAt(0);
        }
    }
}
