package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.widget.Button;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.tencent.smtt.sdk.TbsVideo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.smartRefresh
 * 项目日期：2018/7/19
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class X5VideoActivity extends BaseSwipeBackActivity {

    @BindView(R.id.btn_Video)
    Button btnVideo;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("腾讯X5播放器");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_x5_video;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void detachView() {

    }

    @OnClick(R.id.btn_Video)
    public void onViewClicked() {
        if (TbsVideo.canUseTbsPlayer(getApplicationContext())){
            TbsVideo.openVideo(getApplicationContext(),"android.resource://"+this.getPackageName()+"/"+R.raw.kr36);
        }
    }
}
