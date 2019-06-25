package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage;

import android.os.Bundle;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseFragment;
import com.orhanobut.logger.Logger;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage
 * 项目日期：2019/1/9
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class NeedFragment extends BaseFragment {
    private static final String key = "Need";

    public static NeedFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(key,title);
        NeedFragment needFragment = new NeedFragment();
        needFragment.setArguments(bundle);
        return needFragment;
    }


    @Override
    public int setLayout() {
        return R.layout.fragment_need;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void loadData() {
        Logger.e("NeedFragment——loadData");
    }

    @Override
    protected void detachView() {

    }
}
