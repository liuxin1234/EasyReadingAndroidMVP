package com.liux.easyreadingandroidmvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.adapter
 * 项目日期：2019/1/9
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class CoordinatorAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private String[] mStringList ;

    public CoordinatorAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setCoordinatorBean( String[] mlist){
        this.mStringList = mlist;
    }

    public void setFragments(List<Fragment> fragments){
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStringList[position];
    }
}
