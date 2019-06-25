package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.CoordinatorAdapter;
import com.liux.easyreadingandroidmvp.base.BaseFragment;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage
 * 项目日期：2019/1/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class CoordinatorActivity extends BaseSwipeBackActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_Head)
    ImageView imgHead;
    @BindView(R.id.tv_Edit)
    TextView tvEdit;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.tv_Signature)
    TextView tvSignature;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.sliding_Tab_Layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private String[] tabName = {"全部","产品","需求","问答"};
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private CoordinatorAdapter mCoordinatorAdapter ;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_coordinator;
    }

    @Override
    protected void initView() {
        mCoordinatorAdapter = new CoordinatorAdapter(getSupportFragmentManager());
        mCoordinatorAdapter.setCoordinatorBean(tabName);

        mFragments.add(AllFragment.newInstance(tabName[0]));
        mFragments.add(ProductFragment.newInstance(tabName[1]));
        mFragments.add(NeedFragment.newInstance(tabName[2]));
        mFragments.add(AnswerFragment.newInstance(tabName[3]));

        mCoordinatorAdapter.setFragments(mFragments);
        viewpager.setAdapter(mCoordinatorAdapter);
        slidingTabLayout.setViewPager(viewpager);
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void detachView() {

    }


    @OnClick({R.id.img_back, R.id.tv_Edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_Edit:
                showNormalDialog("功能尚在开发中！！！");
                break;
        }
    }
}
