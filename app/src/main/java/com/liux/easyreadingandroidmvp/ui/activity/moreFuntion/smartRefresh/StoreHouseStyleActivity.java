package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.smartRefresh;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.smartRefresh.adapter.SmartCommonAdapter;
import com.liux.module.smartRefreshBean.SmartRefreshBaseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction.smartRefresh
 * 项目日期：2017/12/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class StoreHouseStyleActivity extends BaseSwipeBackActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private static boolean isFirstEnter = true;
    private SmartCommonAdapter commonAdapter;
    private List<SmartRefreshBaseBean> mList = new ArrayList<>();

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("气球快递");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_store_house_style;
    }

    @Override
    protected void initView() {
        initData();
        initRefresh();
        initAdapter();
    }

    @Override
    protected void detachView() {

    }

    private void initData() {
        mList.clear();
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
        mList.add(new SmartRefreshBaseBean("http://1223","欢迎来到轻松阅！！！"));
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(4000);
            }
        });
        if (isFirstEnter){
            refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
            isFirstEnter = false;
        }
    }

    private void initAdapter() {
        commonAdapter = new SmartCommonAdapter(R.layout.list_item_style_delivery,mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commonAdapter);
    }




}
