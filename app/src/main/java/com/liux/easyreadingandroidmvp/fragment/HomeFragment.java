package com.liux.easyreadingandroidmvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.WXPageAdapter;
import com.liux.easyreadingandroidmvp.base.BaseFragment;
import com.liux.easyreadingandroidmvp.customView.DividerItemDecoration;
import com.liux.easyreadingandroidmvp.presenter.impl.FgHomePresenterImpl;
import com.liux.easyreadingandroidmvp.ui.activity.WebViewActivity;
import com.liux.easyreadingandroidmvp.ui.activity.X5WebView.X5WebViewActivity;
import com.liux.easyreadingandroidmvp.utils.ToastUtil;
import com.liux.easyreadingandroidmvp.view.FgHomeView;
import com.liux.module.wxBean.ArticleSearchBean;
import com.liux.module.wxBean.CategoriesBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by
 * 项目名称：com.liux.easyreading.fragment
 * 项目日期：2017/11/3
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class HomeFragment extends BaseFragment implements FgHomeView {

    private static final String KEY = "EXTRA";
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private String cid;
    private int pageIndex = 1;
    private static final int PAGE_SIZE = 20;
    /**
     * 在上拉刷新的时候，判断，是否处于上拉刷新，如果是的话，就禁止在一次刷新，保障在数据加载完成之前
     * 避免重复和多次加载
     */
    private boolean isLoadMore = false;

    private CategoriesBean.ResultBean mResultBean;

    private List<ArticleSearchBean.ResultBean.ListBean> mResultBeanList = new ArrayList<>();

    private WXPageAdapter mWXPageAdapter;

    FgHomePresenterImpl mFgHomePresenter;

    public static HomeFragment newInstance(CategoriesBean.ResultBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, bean);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mFgHomePresenter = new FgHomePresenterImpl();
        mFgHomePresenter.attachView(this);

        initAdapter();
    }

    @Override
    protected void loadData() {
        initRefreshData();
    }

    @Override
    protected void detachView() {
        mFgHomePresenter.detachView();
    }

    /**
     * 初始化加载
     */
    private void initRefreshData() {

        refreshLayout.setEnableLoadmore(true);

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                initData();
                refreshlayout.finishLoadmore(3000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                initData();
                refreshlayout.finishRefresh(3000);
            }
        });
        refreshLayout.autoRefresh();
    }

    private void initAdapter() {
        mWXPageAdapter = new WXPageAdapter(getActivity(), R.layout.item_layout_wxpage, mResultBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recycleView.setAdapter(mWXPageAdapter);
        mWXPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), X5WebViewActivity.class);
                intent.putExtra("url",mResultBeanList.get(position).getSourceUrl());
                startActivity(intent);
            }
        });

    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mResultBean = (CategoriesBean.ResultBean) bundle.getSerializable(KEY);
            if (mResultBean != null) {
                cid = mResultBean.getCid();
            }
        }
        mFgHomePresenter.loadMobApiData(cid, pageIndex, PAGE_SIZE);

    }


    @Override
    public void showToastMessage(String msg) {
        ToastUtil.showToast(getActivity(), msg);
    }


    @Override
    public void getListDataAdapter(List<ArticleSearchBean.ResultBean.ListBean> resultList) {
        if (resultList != null && resultList.size() > 0){
            if (pageIndex == 1) {
                mResultBeanList.clear();
            }
            //下拉刷新 先clear,再addAll
            mResultBeanList.addAll(resultList);
            mWXPageAdapter.notifyDataSetChanged();
        }
    }


}
