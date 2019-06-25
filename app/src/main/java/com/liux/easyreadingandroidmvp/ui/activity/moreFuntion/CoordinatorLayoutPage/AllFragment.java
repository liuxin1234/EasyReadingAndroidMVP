package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.CommonAdapter;
import com.liux.easyreadingandroidmvp.base.BaseFragment;
import com.liux.easyreadingandroidmvp.customView.DividerItemDecoration;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage
 * 项目日期：2019/1/9
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class AllFragment extends BaseFragment {
    private static final String KEY = "All";
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private CommonAdapter mCommonAdapter;
    private List<String> mList = new ArrayList<>();


    public static AllFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, title);
        AllFragment allFragment = new AllFragment();
        allFragment.setArguments(bundle);
        return allFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_all;
    }

    @Override
    public void initView() {
        mCommonAdapter = new CommonAdapter(getActivity(), R.layout.item_layout_common, mList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(),0));
        recycleView.setAdapter(mCommonAdapter);
        setData();
        setRefreshData();
    }

    @Override
    protected void loadData() {
        //这里会出现2次重复刷新数据，此处有一个bug，因此先抛弃在此处加载数据等逻辑操作
    }

    private void setRefreshData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setData();
                refreshlayout.finishRefresh(3000);
            }
        });
    }

    private void setData() {
        Logger.e("AllFragment加载了！！！");
        for (int i = 0; i < 40; i++) {
            mList.add("特朗普—特没谱");
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    protected void detachView() {

    }


}
