package com.home.dab.datum.demo.recyclerDemo.itemSpread;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.recyclerDemo.DemoBean;

import java.util.List;

/**
 * Created by DAB on 2016/12/29 11:40.
 */

public class DemoApt extends RecyclerView.Adapter<DemoApt.ViewHolder> {
    private static final String TAG = "DemoApt";
    private List<DemoBean> mDemoBeen;
    private LayoutInflater mLayoutInflater;
    private int mSelectPosition;
    private View mLastView;//上一次打开的视图
    private boolean isOnlyOneOpen;//是否允许打开多个视图
    public DemoApt(Context context, List<DemoBean> demoBeen) {
        mDemoBeen = demoBeen;
        mLayoutInflater = LayoutInflater.from(context);
        mSelectPosition = -1;
    }


    public void setOnlyOneOpen(boolean onlyOneOpen) {
        isOnlyOneOpen = onlyOneOpen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_demo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDemoBeen.get(position).getName());
//        holder.mTvDetails.setVisibility(View.VISIBLE);
        //todo 通过数据的的标志来打开和隐藏
        holder.mTvDetails.setVisibility(mSelectPosition == position ? View.VISIBLE : View.GONE);
        holder.mTvDetails.setText("sadsadas\ndsadasda\ndsfsdfsd\n");
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mTvDetails.getVisibility() == View.VISIBLE) {
                    closeItemDetails(holder.mTvDetails);
                } else {
                    // TODO: 2017/1/5  这里需要手动测量宽高,不然下面获取的就为0
                    holder.mTvDetails.measure(holder.mTvDetails.getMeasuredWidthAndState(), holder.mTvDetails.getMeasuredHeightAndState());
                    openItemDetails(holder.mTvDetails);
                    if (isOnlyOneOpen) {
                        if (mLastView != null && mLastView != holder.mTvDetails) {
                            closeItemDetails(mLastView);
                        }
                        mLastView = holder.mTvDetails;
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDemoBeen == null ? 0 : mDemoBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTvDetails;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_demo);
            mTvDetails = (TextView) itemView.findViewById(R.id.tv_details);
        }
    }

    private void openItemDetails(@NonNull View detailsView) {
        int measuredHeight = detailsView.getMeasuredHeight();
        detailsView.setVisibility(View.VISIBLE);
        ObjectAnimator leftViewScaleX = ObjectAnimator.ofFloat(detailsView, "scaleY", 0f, 1f);
        leftViewScaleX.setDuration(300).start();
        leftViewScaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = Float.parseFloat(animation.getAnimatedValue().toString());
                ViewGroup.LayoutParams layoutParams = detailsView.getLayoutParams();
                layoutParams.height = (int) (measuredHeight * animatedValue);

                detailsView.setLayoutParams(layoutParams);
            }
        });

    }


    private void closeItemDetails(@NonNull View detailsView) {
        int measuredHeight = detailsView.getMeasuredHeight();
        ObjectAnimator lastOpenViewScaleX = ObjectAnimator.ofFloat(detailsView, "scaleY", 1f, 0f);
        lastOpenViewScaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = Float.parseFloat(animation.getAnimatedValue().toString());
                ViewGroup.LayoutParams layoutParams = detailsView.getLayoutParams();
                layoutParams.height = (int) (measuredHeight * animatedValue);
                detailsView.setLayoutParams(layoutParams);
            }
        });
        lastOpenViewScaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup.LayoutParams layoutParams = detailsView.getLayoutParams();
                layoutParams.height = measuredHeight;
                detailsView.setLayoutParams(layoutParams);
                detailsView.setVisibility(View.GONE);
            }
        });
        lastOpenViewScaleX.setDuration(300).start();
    }


}
