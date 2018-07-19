package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.CarApiAdapter;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.DividerItemDecoration;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.utils.GsonUtils;
import com.liux.easyreadingandroidmvp.utils.ToastUtil;
import com.liux.module.carBean.CarBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Car;
import com.mob.tools.utils.ResHelper;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：汽车查询界面
 *
 * @author 75095
 */

public class CarAPIActivity extends BaseSwipeBackActivity implements APICallback {

    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private CarApiAdapter mCarApiAdapter;

    private List<CarBean.ResultBean> mBeanList = new ArrayList<CarBean.ResultBean>();

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("汽车信息查询");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_car;
    }

    @Override
    protected void initView() {
//        initData();
        initRefresh();
        initAdapter();
    }

    @Override
    protected void detachView() {

    }

    private void initData() {
        //查询所有汽车品牌
        Car api = ResHelper.forceCast(MobAPI.getAPI(Car.NAME));
        api.queryBrand(this);
    }

    private void initAdapter() {
        mCarApiAdapter = new CarApiAdapter(this, R.layout.item_layout_car, mBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, 1));
        recycleView.setAdapter(mCarApiAdapter);
        mCarApiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast(CarAPIActivity.this, "" + position);
            }
        });
    }

    private void initRefresh() {
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            initData();
            refreshlayout.finishRefresh(3000);
        });
        refreshLayout.autoRefresh();
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        CarBean carBean = GsonUtils.object(map, CarBean.class);
        mBeanList.clear();
        mBeanList.addAll(carBean.getResult());
        mCarApiAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(API api, int action, Throwable throwable) {
        Logger.e(throwable.getMessage());
    }


}
