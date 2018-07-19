package com.liux.easyreadingandroidmvp.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.utils.Util;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity
 * 项目日期：2018/7/18
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class AboutWeActivity extends BaseSwipeBackActivity {


    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_Official_Url)
    TextView tvOfficialUrl;
    @BindView(R.id.btn_UpApp)
    Button btnUpApp;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("关于我们");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_about_we;
    }

    @Override
    protected void initView() {
        String appVersion = Util.getAppVersion(this);
        tvVersion.setText(appVersion);
//        tvOfficialUrl.setText(Html.fromHtml("<a href=\"http://www.nbcei.com\">http://www.nbcei.com</a>"));
//        tvOfficialUrl.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void detachView() {

    }

    @OnClick(R.id.btn_UpApp)
    public void onViewClicked() {
        Beta.checkUpgrade();
    }
}
