package com.liux.easyreadingandroidmvp.presenter;

import com.liux.easyreadingandroidmvp.presenter.base.BasePresenter;
import com.liux.easyreadingandroidmvp.view.FgMainInfoView;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.presenter
 * 项目日期：2018/4/4
 * 作者：liux
 * 功能：处理MainActivity当中的业务逻辑,只为MainActivity服务
 *
 * @author 750954283(qq)
 */

public interface FgMainInfoPresenter extends BasePresenter<FgMainInfoView> {
    //在这里添加MainPresenter特有的方法
    void loadMobApiData() ;
}
