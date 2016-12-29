package com.home.dab.datum.demo.recyclerDemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.dab.datum.R;

import java.util.List;

/**
 * Created by DAB on 2016/12/29 11:40.
 */

public class DemoApt extends RecyclerView.Adapter<DemoApt.ViewHolder> {
    private List<DemoBean> mDemoBeen;
    private LayoutInflater mLayoutInflater;

    public DemoApt(Context context, List<DemoBean> demoBeen) {
        mDemoBeen = demoBeen;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_demo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDemoBeen.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDemoBeen == null ? 0 : mDemoBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_demo);
        }
    }
}
