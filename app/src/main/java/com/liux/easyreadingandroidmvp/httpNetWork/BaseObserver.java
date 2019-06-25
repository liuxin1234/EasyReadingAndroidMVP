package com.liux.easyreadingandroidmvp.httpNetWork;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;

import com.liux.easyreadingandroidmvp.httpNetWork.bean.BaseEntity;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.httpNetWork
 * 项目日期：2018/7/3
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    protected Context mContext;
    
    public BaseObserver(){
        
    }
    
    public BaseObserver(Context context){
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        onRequestEnd();
        if (tBaseEntity.isSuccess()){
            try {
                onSuccess(tBaseEntity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                onCodeError(tBaseEntity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException){
                onFailure(e, true);
            }else {
                onFailure(e, false);
            }
        }catch (Exception et){
            et.printStackTrace();
        }
    }


    @Override
    public void onComplete() {

    }

    /**
     *  返回成功
     * @param tBaseEntity
     * @throws Exception
     */
    protected abstract void onSuccess(BaseEntity<T> tBaseEntity) throws Exception;

    /**
     *  返回成功了,但是code错误
     * @param tBaseEntity
     * @throws Exception
     */
    protected abstract void onCodeError(BaseEntity<T> tBaseEntity) throws Exception;

    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart(){

    };

    protected void onRequestEnd(){
        closeProgressDialog();
    }

    public void showProgressDialog(){
//        ProgressDialog.show(mContext,false,"请稍后");
    }

    private void closeProgressDialog() {
//        ProgressDialog.cancle();
    }


}
