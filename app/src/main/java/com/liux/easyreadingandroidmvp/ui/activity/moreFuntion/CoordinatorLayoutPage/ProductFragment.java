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

public class ProductFragment extends BaseFragment {
    private static final String key = "product";

    public static ProductFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(key,title);
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);
        return productFragment;
    }


    @Override
    public int setLayout() {
        return R.layout.fragment_product;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void loadData() {
        Logger.e("ProductFragment——loadData");
    }

    @Override
    protected void detachView() {

    }
}
