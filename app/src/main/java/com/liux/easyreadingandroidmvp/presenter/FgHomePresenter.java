package com.liux.easyreadingandroidmvp.presenter;

import com.liux.easyreadingandroidmvp.presenter.base.BasePresenter;
import com.liux.easyreadingandroidmvp.view.FgHomeView;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.presenter
 * 项目日期：2018/4/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public interface FgHomePresenter extends BasePresenter<FgHomeView>{
    //在这里添加MainPresenter特有的方法
    void loadMobApiData(String cid,int pageIndex,int PAGE_SIZE) ;
}
