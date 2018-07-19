package com.liux.easyreadingandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.customView.DividerItemDecoration;
import com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CarSeriesNameActivity;
import com.liux.module.carBean.CarBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：
 */

public class CarApiAdapter extends BaseQuickAdapter<CarBean.ResultBean,BaseViewHolder> {

    private Context mContext;
    private CarBrandAdapter mCarBrandAdapter;
    private List<CarBean.ResultBean.SonBean> mSonBeanList = new ArrayList<>();

    public CarApiAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<CarBean.ResultBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CarBean.ResultBean item) {
        helper.setText(R.id.tv_title_car_brand,"汽车品牌："+item.getName());
        mSonBeanList.clear();
        mSonBeanList.addAll(item.getSon());
        RecyclerView rvView = helper.getView(R.id.rv_car_brand);
        mCarBrandAdapter = new CarBrandAdapter(R.layout.item_layout_car_brand,mSonBeanList);
        rvView.setLayoutManager(new LinearLayoutManager(mContext));
        rvView.addItemDecoration(new DividerItemDecoration(mContext,1));
        rvView.setAdapter(mCarBrandAdapter);
        mCarBrandAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //注意这里不能用intent.putExtra("sonBean", (Serializable) mSonBeanList.get(position)); 会导致数据错乱
                Intent intent = new Intent(mContext, CarSeriesNameActivity.class);
                intent.putExtra("sonBean", (Serializable) item.getSon().get(position));
                mContext.startActivity(intent);
            }
        });
    }
}
