package com.liux.easyreadingandroidmvp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreadingandroidmvp.R;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.adapter
 * 项目日期：2019/1/10
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class CommonAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public CommonAdapter(Context context,int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_Name,item);
    }
}
