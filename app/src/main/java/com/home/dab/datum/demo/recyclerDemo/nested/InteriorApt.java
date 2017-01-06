package com.home.dab.datum.demo.recyclerDemo.nested;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.recyclerDemo.DemoBean;

import java.util.List;

/**
 * Created by DAB on 2017/1/6 13:19.
 */

public class InteriorApt extends RecyclerView.Adapter<InteriorApt.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<DemoBean> mDemoBeen;

    public InteriorApt(Context context, List<DemoBean> demoBeen) {
        mDemoBeen = demoBeen;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public InteriorApt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_demo_nested_interior, parent, false));
    }

    @Override
    public void onBindViewHolder(InteriorApt.ViewHolder holder, int position) {
        holder.mTextView.setText(mDemoBeen.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDemoBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_demo_nested_interior);
        }
    }
}
