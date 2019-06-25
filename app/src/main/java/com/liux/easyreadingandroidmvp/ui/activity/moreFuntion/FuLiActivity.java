package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.FuLiAdapter;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.common.Contants;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.presenter.AcFuLiPresenter;
import com.liux.easyreadingandroidmvp.presenter.impl.AcFuLiPresenterImpl;
import com.liux.easyreadingandroidmvp.utils.ToastUtil;
import com.liux.easyreadingandroidmvp.view.AcFuLiView;
import com.liux.module.GankBean.ResultBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/26
 * 作者：liux
 * 功能：美图福利
 *
 * @author 750954283(qq)
 */

public class FuLiActivity extends BaseSwipeBackActivity implements AcFuLiView {
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private int pageIndex = 1;
    private static final int PAGE_SIZE = 20;
    private FuLiAdapter mFuLiAdapter;
    private List<ResultBean> mList = new ArrayList<>();
    AcFuLiPresenter mAcFuLiPresenter;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("美图福利");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fuli;
    }

    @Override
    protected void initView() {
        mAcFuLiPresenter = new AcFuLiPresenterImpl();
        mAcFuLiPresenter.attachView(this);
        initData();
        initAdapter();
        initRefreshData();
    }

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
    }


    private void initAdapter() {
        mFuLiAdapter = new FuLiAdapter(this, R.layout.item_layout_fuli, mList);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(mFuLiAdapter);
        mFuLiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(FuLiActivity.this, FuliDetailActivity.class);
                intent.putExtra(Contants.FULI_DETAIL, mList.get(position).getUrl());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.image_id);
                    startActivity(intent, ActivityOptions.
                            makeSceneTransitionAnimation(FuLiActivity.this, imageView, getString(R.string.transition_photos)).toBundle());
                } else {
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(FuLiActivity.this, intent, options.toBundle());
                }
            }
        });
    }

    private void initData() {
        mAcFuLiPresenter.loadFuLiData(pageIndex);
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
        ToastUtil.showToast(this, "点击了浮动按钮");
    }

    @Override
    public void showToastMessage(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void loadFuLiData(List<ResultBean> results) {
        if (results != null && results.size() > 0){
            if (pageIndex == 1){
                mList.clear();
            }
            //下拉刷新 先clear,再addAll
            mList.addAll(results);
            mFuLiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void detachView() {
        mAcFuLiPresenter.detachView();
    }
}
