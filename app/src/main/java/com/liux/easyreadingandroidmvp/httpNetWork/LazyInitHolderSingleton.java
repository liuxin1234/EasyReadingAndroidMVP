package com.liux.easyreadingandroidmvp.httpNetWork;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.httpNetWork
 * 项目日期：2018/4/10
 * 作者：liux
 * 功能：登记式模式（holder） 单例目前最好的单例模式
 *
 * @author 750954283(qq)
 */

public class LazyInitHolderSingleton {
    //构造方法是私有的，从而避免外界利用构造方法直接创建任意多实例
    private  LazyInitHolderSingleton() {

    }
    //内部类只有在外部类被调用才加载，产生INSTANCE实例；又不用加锁
    private static class SingletonHolder{
        private static final LazyInitHolderSingleton INSTANCE = new LazyInitHolderSingleton();
    }

    public static LazyInitHolderSingleton getInstance(){
        return SingletonHolder.INSTANCE;
    }

}
