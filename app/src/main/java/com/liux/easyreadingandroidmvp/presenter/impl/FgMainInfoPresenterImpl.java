package com.liux.easyreadingandroidmvp.presenter.impl;


import com.liux.easyreadingandroidmvp.presenter.FgMainInfoPresenter;
import com.liux.easyreadingandroidmvp.utils.GsonUtils;
import com.liux.easyreadingandroidmvp.view.FgMainInfoView;
import com.liux.module.wxBean.CategoriesBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.WxArticle;

import java.util.List;
import java.util.Map;

/**
 * <p>Description:
 * 负责处理获取数据和处理，并调用MainInfoBaseView的接口方法 传递数据
 * @author xzhang
 */

public class FgMainInfoPresenterImpl implements FgMainInfoPresenter {

    private FgMainInfoView mainBaseView ;

    @Override
    public void attachView(FgMainInfoView view) {
        this.mainBaseView = view ;
    }

    @Override
    public void detachView() {
        mainBaseView = null ;
    }



    @Override
    public void loadMobApiData() {
        WxArticle wxArticle = (WxArticle) MobAPI.getAPI(WxArticle.NAME);
        wxArticle.queryCategory(new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                CategoriesBean categoriesBean = GsonUtils.object(map,CategoriesBean.class);
                List<CategoriesBean.ResultBean> result = categoriesBean.getResult();
                mainBaseView.getListDataAdapter(result);
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {
                mainBaseView.showToastMessage(""+throwable.getMessage());
            }
        });
    }
}
