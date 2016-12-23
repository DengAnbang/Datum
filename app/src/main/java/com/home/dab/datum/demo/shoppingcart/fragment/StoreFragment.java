package com.home.dab.datum.demo.shoppingcart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.shoppingcart.Data;
import com.home.dab.datum.demo.shoppingcart.ShoppingMainActivity;
import com.home.dab.datum.demo.shoppingcart.adapter.CommodityApt;
import com.home.dab.datum.demo.shoppingcart.adapter.NavigationApt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;


/**
 * Created by DAB on 2016/12/13 13:54.
 *
 */

public class StoreFragment extends Fragment {
    private static final String TAG = "StoreFragment";
    private View mView;
    private RecyclerView mRecyclerNavigation, mRecyclerCommodity;
    private CommodityApt mCommodityApt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_store, container, false);
        initView(mView);
        initData();

        return mView;
    }

    private void initData() {
        Random random = new Random();
        List<Data> datas = new ArrayList<>();
        for (int i = 0; i < 10 + random.nextInt(10); i++) {
            datas.add(new Data(i, "商品分类" + i));
        }
        List<Data.Commodity> commodities = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChildFirstPosition(commodities.size());
            for (int j = 0; j < datas.get(i).getCommodities().size(); j++) {
                commodities.add(datas.get(i).getCommodities().get(j));
            }
        }
        mRecyclerNavigation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NavigationApt navigationApt = new NavigationApt(datas, getActivity());
        mRecyclerNavigation.setAdapter(navigationApt);
        navigationApt.setOnItemOnClickListener((view, position) -> {
            NavigationApt.select = position;
            navigationApt.notifyDataSetChanged();
            int childFirstPosition = datas.get(position).getChildFirstPosition();
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerCommodity.getLayoutManager();
            linearLayoutManager.scrollToPositionWithOffset(childFirstPosition, 0);
            mCommodityApt.notifyDataSetChanged();
        });


        mRecyclerCommodity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置悬浮索引
        mCommodityApt = new CommodityApt(getActivity(), commodities);
        StickyHeaderDecoration decor = new StickyHeaderDecoration(mCommodityApt);
        mRecyclerCommodity.setAdapter(mCommodityApt);
        mRecyclerCommodity.addItemDecoration(decor, 0);
        mRecyclerCommodity.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int ownerId = commodities.get(firstItemPosition).getOwnerId();
                    mRecyclerNavigation.smoothScrollToPosition(ownerId);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerNavigation.getLayoutManager();
                    linearLayoutManager.scrollToPositionWithOffset(ownerId, 0);
                    NavigationApt.select = ownerId;
                    navigationApt.notifyDataSetChanged();
                }
            }
        });
        ShoppingMainActivity activity = (ShoppingMainActivity) getActivity();
        activity.getRefreshView().setConflictView(mRecyclerCommodity);
        activity.getRefreshView().setConflictView(mRecyclerNavigation);
    }

    private void initView(View view) {
        mRecyclerNavigation = (RecyclerView) view.findViewById(R.id.rv_navigation);
        mRecyclerCommodity = (RecyclerView) view.findViewById(R.id.rv_commodity);
    }
}
