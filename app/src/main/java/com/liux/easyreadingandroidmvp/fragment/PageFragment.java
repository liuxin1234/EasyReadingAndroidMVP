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
import com.liux.easyreadingandroidmvp.presenter.impl.FgPagePresenterImpl;
import com.liux.easyreadingandroidmvp.ui.activity.WebViewActivity;
import com.liux.easyreadingandroidmvp.ui.activity.X5WebView.X5WebViewActivity;
import com.liux.easyreadingandroidmvp.utils.ToastUtil;
import com.liux.easyreadingandroidmvp.view.FgPageView;
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

public class PageFragment extends BaseFragment implements FgPageView {

    private static final String KEY = "EXTRA";
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private WXPageAdapter mWXPageAdapter;
    private int pageIndex = 0;
    private static final int PAGE_SIZE = 20;
    private String cid;
    private boolean isLoadMore = false;

    private CategoriesBean.ResultBean bean;
    private List<ArticleSearchBean.ResultBean.ListBean> mBeanList = new ArrayList<>();

    FgPagePresenterImpl mFgPagePresenter;


    public static PageFragment newInstance(CategoriesBean.ResultBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, bean);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_page;
    }

    @Override
    public void initView() {
        mFgPagePresenter = new FgPagePresenterImpl();
        mFgPagePresenter.attachView(this);
        initAdapter();
        initRefresh();
    }

    @Override
    protected void loadData() {
        /**
         * 这里加载数据是为了让用户更快的看见数据，增加体验性，但是注意上面initView中
         * initRefresh（）方法使用了自动下拉刷新，会出现2次加载数据的方法。
         * 若是为了节省流量，可以将initRefresh（）放到这里，删除initData()方法
         */
        initData();
    }

    @Override
    protected void detachView() {
        mFgPagePresenter.detachView();
    }

    private void initRefresh() {
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
        mWXPageAdapter = new WXPageAdapter(getActivity(), R.layout.item_layout_wxpage, mBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recycleView.setAdapter(mWXPageAdapter);
        mWXPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), X5WebViewActivity.class);
                intent.putExtra("url", mBeanList.get(position).getSourceUrl());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (CategoriesBean.ResultBean) bundle.getSerializable(KEY);
            if (bean != null) {
                cid = bean.getCid();
            }
            mFgPagePresenter.loadMobApiData(cid,pageIndex,PAGE_SIZE);

//            try {
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
    }


    @Override
    public void showToastMessage(String msg) {
        ToastUtil.showToast(getActivity(),msg);
    }


    @Override
    public void getListDataAdapter(List<ArticleSearchBean.ResultBean.ListBean> listBeen) {
        if (listBeen != null && listBeen.size() > 0){
            if (pageIndex == 1){
                mBeanList.clear();
            }
            mBeanList.addAll(listBeen);
            mWXPageAdapter.notifyDataSetChanged();
        }

    }
}
