package com.home.dab.datum.demo.md.coordinatorLayout.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.dab.datum.R;

import java.util.List;

/**
 * Created by DAB on 2016/12/27 09:21.
 */

public class HeadApt extends RecyclerView.Adapter<HeadApt.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<String> mStrings;

    public HeadApt(Context context, List<String> strings) {
        mStrings = strings;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_recyler_head, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_recyler_head);
        }
    }
}
