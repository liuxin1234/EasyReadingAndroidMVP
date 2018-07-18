package com.liux.easyreadingandroidmvp.httpNetWork.bean;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.httpNetWork.bean
 * 项目日期：2018/7/3
 * 作者：liux
 * 功能： 解析实体基类
 *
 * @author 750954283(qq)
 */

public class BaseEntity<T> {
    private static int SUCCESS_CODE = 200;//成功的code
    private int code;
    private String msg;
    private T data;


    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
