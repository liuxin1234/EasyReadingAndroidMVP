package com.liux.easyreadingandroidmvp.presenter;

import com.liux.easyreadingandroidmvp.presenter.base.BasePresenter;
import com.liux.easyreadingandroidmvp.view.AcFuLiView;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.presenter
 * 项目日期：2018/4/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public interface AcFuLiPresenter extends BasePresenter<AcFuLiView> {
    void loadFuLiData(int pageIndex);
}
