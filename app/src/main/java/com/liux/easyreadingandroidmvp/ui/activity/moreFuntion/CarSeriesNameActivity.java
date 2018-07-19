package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.CarSeriesNameAdapter;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.DividerItemDecoration;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.utils.GsonUtils;
import com.liux.easyreadingandroidmvp.utils.ToastUtil;
import com.liux.module.carBean.CarBean;
import com.liux.module.carBean.CarSeriesNameBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Car;
import com.mob.tools.utils.ResHelper;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/4
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class CarSeriesNameActivity extends BaseSwipeBackActivity implements APICallback {


    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private List<CarSeriesNameBean.ResultBean> mResultBeanList = new ArrayList<>();
    private CarBean.ResultBean.SonBean mSonBean;

    private CarSeriesNameAdapter mCarSeriesNameAdapter;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("品牌汽车信息列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_car_series_name;
    }

    @Override
    protected void initView() {
        Serializable bean = getIntent().getSerializableExtra("sonBean");
        if (bean != null) {
            mSonBean = (CarBean.ResultBean.SonBean) bean;
        }
        initData();


        initRefresh();
        initAdapter();
    }

    @Override
    protected void detachView() {

    }

    private void initData() {
        //查询车型详细信息 name = type
        Car api = ResHelper.forceCast(MobAPI.getAPI(Car.NAME));
        api.querySeriesName(mSonBean.getType(), this);
    }


    private void initRefresh() {
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            initData();
            refreshlayout.finishRefresh(3000);
        });
    }

    private void initAdapter() {
        mCarSeriesNameAdapter = new CarSeriesNameAdapter(this, R.layout.item_layout_car_series_name, mResultBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, 1));
        recycleView.setAdapter(mCarSeriesNameAdapter);
        mCarSeriesNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CarSeriesNameActivity.this, CarSeriesDetailActivity.class);
                intent.putExtra("cid", mResultBeanList.get(position).getCarId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        CarSeriesNameBean carSeriesNameBean = GsonUtils.object(map, CarSeriesNameBean.class);
        mResultBeanList.clear();
        mResultBeanList.addAll(carSeriesNameBean.getResult());
        mCarSeriesNameAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(API api, int i, Throwable throwable) {
        ToastUtil.showToast(this, throwable.getMessage());
        Logger.e(throwable.getMessage());
    }


}
