package com.home.dab.datum.demo.recyclerDemo.nested;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.recyclerDemo.DemoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAB on 2017/1/6 11:26.
 */

public class NestedApt extends RecyclerView.Adapter<NestedApt.ViewHolder> {
    private static final String TAG = "DemoApt";
    private LayoutInflater mLayoutInflater;
    private List<List<DemoBean>> mLists;

    public NestedApt(Context context, List<DemoBean> demoBeen) {
        mLayoutInflater = LayoutInflater.from(context);
        mLists = new ArrayList<>();
        //模拟数据
        if (demoBeen != null && demoBeen.size() > 0) {
            List<DemoBean> demoBeanList = new ArrayList<>();
            demoBeanList.add(demoBeen.get(0));
            for (int i = 1; i < demoBeen.size(); i++) {
                if (demoBeen.get(i).getNum() != demoBeen.get(i - 1).getNum()) {
                    mLists.add(demoBeanList);
                    demoBeanList = new ArrayList<>();
                    demoBeanList.add(demoBeen.get(i));
                } else {
                    demoBeanList.add(demoBeen.get(i));
                }
            }
            mLists.add(demoBeanList);
        }

    }


    @Override
    public NestedApt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NestedApt.ViewHolder(mLayoutInflater.inflate(R.layout.item_demo_nested, parent, false));
    }

    @Override
    public void onBindViewHolder(NestedApt.ViewHolder holder, int position) {
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 4, GridLayoutManager.VERTICAL, false));
        holder.mRecyclerView.setAdapter(new InteriorApt(holder.itemView.getContext(), mLists.get(position)));
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_item_demo_nested);
        }
    }


}
