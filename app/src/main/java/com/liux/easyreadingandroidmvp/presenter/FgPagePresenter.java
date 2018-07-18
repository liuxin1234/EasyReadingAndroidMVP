package com.liux.easyreadingandroidmvp.presenter;

import com.liux.easyreadingandroidmvp.presenter.base.BasePresenter;
import com.liux.easyreadingandroidmvp.view.FgPageView;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.presenter
 * 项目日期：2018/4/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public interface FgPagePresenter extends BasePresenter<FgPageView> {
    void loadMobApiData(String cid,int pageIndex,int PAGE_SIZE);
}
