package com.liux.easyreadingandroidmvp.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liux.easyreadingandroidmvp.R;
import com.liux.module.moreFunctionBean.FunctionBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class MoreFunctionAdapter extends BaseAdapter {

    private Context mContext;
    private List<FunctionBean> mList = new ArrayList<>();
    private LayoutInflater mInflater;


    public MoreFunctionAdapter(Context context, List<FunctionBean> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.items_more_function, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FunctionBean functionBean = mList.get(position);
        //注意事项：setBackgroundColor方法【一般用于RelativeLayout、TextView等控件】 setImageDrawable方法【一般用于ImageView控件】
        viewHolder.imgGridButtonLogoBtm.setImageDrawable(new ColorDrawable(ContextCompat.getColor(mContext,functionBean.getIcoBgColor())));
        viewHolder.imgGridButtonLogoTop.setImageResource(functionBean.getIcoName());
        viewHolder.tvGridButtonTitle.setText(functionBean.getActivityTitle());

        return convertView;
    }

     static class ViewHolder {
        @BindView(R.id.img_GridButton_logo_btm)
        RoundedImageView imgGridButtonLogoBtm;
        @BindView(R.id.img_GridButton_logo_top)
        RoundedImageView imgGridButtonLogoTop;
        @BindView(R.id.tv_GridButton_title)
        TextView tvGridButtonTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
