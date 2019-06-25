package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage.CoordinatorActivity;
import com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.pickImageActivity.WxDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2018/3/30
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class WeiXinFriendActivity extends BaseSwipeBackActivity {

    @BindView(R.id.btn_WeiXin_PickImage)
    Button btnWeiXinPickImage;
    @BindView(R.id.btn_Other_PickImage)
    Button btnOtherPickImage;
    @BindView(R.id.btn_Coordinator)
    Button btnCoordinator;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("仿微信朋友圈");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_weixin_friend;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void detachView() {

    }


    @OnClick({R.id.btn_WeiXin_PickImage, R.id.btn_Other_PickImage, R.id.btn_Coordinator})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_WeiXin_PickImage:
                intent.setClass(this, WxDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_Other_PickImage:

                break;
            case R.id.btn_Coordinator:
                intent.setClass(this,CoordinatorActivity.class);
                startActivity(intent);
                break;
        }
    }
}
