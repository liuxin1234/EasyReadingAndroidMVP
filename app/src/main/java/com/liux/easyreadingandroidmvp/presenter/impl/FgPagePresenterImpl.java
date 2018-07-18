package com.liux.easyreadingandroidmvp.presenter.impl;

import com.liux.easyreadingandroidmvp.presenter.FgPagePresenter;
import com.liux.easyreadingandroidmvp.utils.GsonUtils;
import com.liux.easyreadingandroidmvp.view.FgPageView;
import com.liux.module.wxBean.ArticleSearchBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.WxArticle;

import java.util.List;
import java.util.Map;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.presenter.impl
 * 项目日期：2018/4/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class FgPagePresenterImpl implements FgPagePresenter,APICallback {

    private FgPageView mFgPageView;


    @Override
    public void attachView(FgPageView view) {
        this.mFgPageView = view;
    }

    @Override
    public void detachView() {
        mFgPageView = null;
    }

    @Override
    public void loadMobApiData(String cid, int pageIndex, int PAGE_SIZE) {
        WxArticle wxArticle = new WxArticle();
        wxArticle.searchArticleList(cid, pageIndex, PAGE_SIZE, this);
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        ArticleSearchBean articleSearchBean = GsonUtils.object(map, ArticleSearchBean.class);
        List<ArticleSearchBean.ResultBean.ListBean> listBeen = articleSearchBean.getResult().getList();
        mFgPageView.getListDataAdapter(listBeen);
    }

    @Override
    public void onError(API api, int i, Throwable throwable) {
        mFgPageView.showToastMessage(throwable.getMessage());
    }


}
