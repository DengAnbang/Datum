package com.home.dab.datum.demo.shoppingcart.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by DAB on 2016/12/13 14:48.
 */

public class ViewPagerApt extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public ViewPagerApt(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
