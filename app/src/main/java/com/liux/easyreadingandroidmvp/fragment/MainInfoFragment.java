package com.liux.easyreadingandroidmvp.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.FixedPagerAdapter;
import com.liux.easyreadingandroidmvp.base.BaseFragment;
import com.liux.easyreadingandroidmvp.presenter.impl.FgMainInfoPresenterImpl;
import com.liux.easyreadingandroidmvp.ui.MainActivity;
import com.liux.easyreadingandroidmvp.utils.ToastUtil;
import com.liux.easyreadingandroidmvp.view.FgMainInfoView;
import com.liux.module.wxBean.CategoriesBean;
import com.mingle.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by
 * 项目名称：com.liux.easyreading.fragment
 * 项目日期：2017/11/2
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class MainInfoFragment extends BaseFragment implements ViewPager.OnPageChangeListener,FgMainInfoView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.info_viewpager)
    ViewPager infoViewpager;
    @BindView(R.id.loadView)
    LoadingView loadView;


    private List<Fragment> mFragments;
    private FixedPagerAdapter mPagerAdapter;
    private List<CategoriesBean.ResultBean> mResultBeanList = new ArrayList<>();

    FgMainInfoPresenterImpl mMainInfoPresenter;

    @Override
    public int setLayout() {
        return R.layout.fragment_main_info;
    }

    @Override
    public void initView() {
        mMainInfoPresenter = new FgMainInfoPresenterImpl();
        mMainInfoPresenter.attachView(this);

        loadView.setVisibility(View.VISIBLE);
        initListener();
    }

    @Override
    protected void loadData() {
        mMainInfoPresenter.loadMobApiData();
    }

    @Override
    protected void detachView() {
        mMainInfoPresenter.detachView();
    }

    private void initListener() {
        infoViewpager.addOnPageChangeListener(this);
    }

    private void initAdapter() {
        mPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mPagerAdapter.setCategoriesBeen(mResultBeanList);
        mFragments = new ArrayList<Fragment>();
        for (int i = 0; i < mResultBeanList.size(); i++) {
            BaseFragment fragment = null;
            if (i == 0) {
                //当前为第一个fragment
                fragment = HomeFragment.newInstance(mResultBeanList.get(i));
            } else {
                fragment = PageFragment.newInstance(mResultBeanList.get(i));
            }
            mFragments.add(fragment);
        }
        mPagerAdapter.setFragments(mFragments);
        infoViewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(infoViewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        loadView.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            //当viewPage滑动到第一个页面时，侧滑菜单才能进行侧滑出来，这样就不会与viewPage的滑动起冲突
            ((MainActivity) getActivity()).getDragLayout().setIsDrag(true);
        } else {
            ((MainActivity) getActivity()).getDragLayout().setIsDrag(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void showToastMessage(String msg) {
        ToastUtil.showToast(getActivity(),msg);
    }

    @Override
    public void getListDataAdapter(List<CategoriesBean.ResultBean> resultBeanList) {
        if (resultBeanList != null && resultBeanList.size() > 0){
            mResultBeanList.addAll(resultBeanList);
            initAdapter();
        }
    }


}
