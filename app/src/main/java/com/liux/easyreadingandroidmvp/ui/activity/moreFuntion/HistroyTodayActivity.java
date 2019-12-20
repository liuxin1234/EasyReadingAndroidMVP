package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.HistoryTodayAdapter;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.utils.DateUtils;
import com.liux.easyreadingandroidmvp.utils.GsonUtils;
import com.liux.easyreadingandroidmvp.widget.TopLayoutManager;
import com.liux.module.historyTodayBean.historyBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.History;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：历史上的今天
 *
 * @author 75095
 */

public class HistroyTodayActivity extends BaseSwipeBackActivity {

    @BindView(R.id.rv_historyToday)
    RecyclerView rvHistoryToday;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private HistoryTodayAdapter mHistoryTodayAdapter;

    private List<historyBean.ResultBean> mResultBeen = new ArrayList<historyBean.ResultBean>();

    private boolean isShowLoadMore = false;


    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("历史上的今天");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_history_today;
    }

    @Override
    protected void initView() {
//        initData();
        initAdapter();
        initRefresh();
    }

    @Override
    protected void detachView() {

    }

    private void initRefresh() {
        refreshLayout.setEnableLoadmore(isShowLoadMore);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                refreshLayout.finishRefresh(3000);
            }
        });

        refreshLayout.autoRefresh();
    }

    private void initData() {
        String shortMD = DateUtils.getShortMD();

        History history = new History();
        history.queryHistory(shortMD, new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                historyBean historyBean = GsonUtils.object(map, com.liux.module.historyTodayBean.historyBean.class);
                List<com.liux.module.historyTodayBean.historyBean.ResultBean> result = historyBean.getResult();
                mResultBeen.addAll(result);
                mHistoryTodayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });
    }

    private void initAdapter() {
        mHistoryTodayAdapter = new HistoryTodayAdapter(R.layout.item_layout_history_today, mResultBeen);
        LinearLayoutManager manager = new TopLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvHistoryToday.setLayoutManager(manager);
        rvHistoryToday.setAdapter(mHistoryTodayAdapter);
        mHistoryTodayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HistroyTodayActivity.this, HistoryDetailActivity.class);
                intent.putExtra("dataList", (Serializable) mResultBeen.get(position));
                startActivity(intent);
            }
        });
        mHistoryTodayAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                historyBean.ResultBean resultBean = mResultBeen.get(position);
                mResultBeen.remove(position);
                mHistoryTodayAdapter.notifyItemRemoved(position);
                mHistoryTodayAdapter.notifyItemRangeChanged(0,mResultBeen.size()-position);

                mResultBeen.add(0,resultBean);
                mHistoryTodayAdapter.notifyItemInserted(0);
                mHistoryTodayAdapter.notifyItemRangeChanged(0, mResultBeen.size());
                Toast.makeText(getApplicationContext(),"置顶成功",Toast.LENGTH_SHORT).show();
//                rvHistoryToday.smoothScrollToPosition(0); //这个置顶滑动有部分bug
                return true;
            }
        });
    }


}
