package com.home.dab.datum.demo.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.shoppingcart.Data;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;


/**
 * Created by DAB on 2016/12/13 15:04.
 */

public class CommodityApt extends RecyclerView.Adapter<CommodityApt.ViewHodler>
        implements StickyHeaderAdapter<CommodityApt.HeaderHolder> {
    private static final String TAG = "CommodityApt";
    private LayoutInflater mInflater;
    private List<Data.Commodity> mCommodities;

    public CommodityApt(Context context, List<Data.Commodity> mCommodities) {
        mInflater = LayoutInflater.from(context);
        this.mCommodities = mCommodities;

    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(mInflater.inflate(R.layout.item_commodity, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        holder.mTextView.setText(mCommodities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCommodities.size();
    }


    @Override
    public long getHeaderId(int position) {
        return mCommodities.get(position).getOwnerId();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return new HeaderHolder(mInflater.inflate(R.layout.item_contacts_head, viewGroup, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder headerHolder, int i) {
        headerHolder.mTextView.setText(mCommodities.get(i).getOwenerTitle());
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHodler(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_commodity_name);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public HeaderHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_head);
        }
    }
}
