package com.liux.easyreadingandroidmvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.greenDao.entity.UserBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2018/3/29
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class GreenDaoUserAdapter extends BaseQuickAdapter<UserBean,BaseViewHolder> {

    public GreenDaoUserAdapter(int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tv_Name,item.getUserName())
                .setText(R.id.tv_PassWord,item.getPassWord())
                .setText(R.id.tv_Age,String.valueOf(item.getAge()))
                .setText(R.id.tv_Sex,item.getSex());
    }
}
