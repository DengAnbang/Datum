package com.home.dab.datum.demo.shoppingcart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.shoppingcart.adapter.ViewPagerApt;
import com.home.dab.datum.demo.shoppingcart.fragment.StoreFragment;
import com.home.dab.datum.demo.shoppingcart.fragment.TestFragment;
import com.home.dab.datum.demo.shoppingcart.fragment.TestFragment2;
import com.home.dab.datum.demo.view.RefreshView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class ShoppingMainActivity extends AppCompatActivity {
    private ViewPager mVpContent;
    private RefreshView mRefreshView;
    private TextView mTvRefreshHint;

    //这里提供出去,让fragment里面滑动冲突的view添加进去refreshHint
    public RefreshView getRefreshView() {
        return mRefreshView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_main);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mRefreshView = (RefreshView) findViewById(R.id.rv_refresh);
        mTvRefreshHint = (TextView) findViewById(R.id.tv_refresh_hint);
        initData();
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        StoreFragment storeFragment = new StoreFragment();
        fragments.add(storeFragment);
        fragments.add(new TestFragment());
        fragments.add(new TestFragment2());
        ViewPagerApt viewPagerApt = new ViewPagerApt(getSupportFragmentManager(), fragments);
        mVpContent.setAdapter(viewPagerApt);
        mRefreshView.setOnPullDownDistanceChange((startRefreshDistance, distance) -> mTvRefreshHint.setText("开始刷新的距离:" + startRefreshDistance + "当前拉的距离" + distance));
        mRefreshView.setOnRefreshing(() -> {
            mTvRefreshHint.setText("刷新中...");
            Observable.timer(2000, TimeUnit.MILLISECONDS)
                    .subscribe(aLong -> mRefreshView.close());
        });

    }
}