package com.liux.easyreadingandroidmvp.presenter.impl;

import com.liux.easyreadingandroidmvp.httpNetWork.Api.ApiConstants;
import com.liux.easyreadingandroidmvp.httpNetWork.Api.HostType;
import com.liux.easyreadingandroidmvp.httpNetWork.BaseGankObserver;
import com.liux.easyreadingandroidmvp.httpNetWork.RetrofitManager;
import com.liux.easyreadingandroidmvp.httpNetWork.RetrofitService;
import com.liux.easyreadingandroidmvp.presenter.AcFuLiPresenter;
import com.liux.easyreadingandroidmvp.utils.GsonUtils;
import com.liux.easyreadingandroidmvp.view.AcFuLiView;
import com.liux.module.GankBean.BaseBean;
import com.liux.module.GankBean.ResultBean;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.presenter.impl
 * 项目日期：2018/4/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class AcFuLiPresenterImpl implements AcFuLiPresenter {

    private RetrofitService mRetrofitService;
    private AcFuLiView mAcFuLiView;
    @Override
    public void attachView(AcFuLiView view) {
        this.mAcFuLiView = view;
    }

    @Override
    public void detachView() {
        mAcFuLiView = null;
    }

    @Override
    public void loadFuLiData(int pageIndex) {
        mRetrofitService = RetrofitManager.getInstance(HostType.GANK_IO_DATA).getRetrofitService();
        mRetrofitService.getNewsList(ApiConstants.GANDK_IO_MEIZI, pageIndex)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseGankObserver<List<ResultBean>>() {
                        @Override
                        protected void onSuccess(BaseBean<List<ResultBean>> listBaseBean) throws Exception {
                            if (listBaseBean != null) {
                                List<ResultBean> results = listBaseBean.getResults();
                                GsonUtils.toJson(results);
                                mAcFuLiView.loadFuLiData(results);
                            }
                        }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mAcFuLiView.showToastMessage(e.getMessage());
                    }
                });

    }
}
