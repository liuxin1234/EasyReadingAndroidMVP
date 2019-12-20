package com.liux.easyreadingandroidmvp.greenDao.dao;

import com.liux.easyreadingandroidmvp.greenDao.GreenDaoManager;
import com.liux.easyreadingandroidmvp.greenDao.entity.UserBean;
import com.liux.easyreadingandroidmvp.greenDao.entity.UserBeanDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.greenDao
 * 项目日期：2018/3/29
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class UserDaoModify {

    private final GreenDaoManager mDaoManager;
    private static UserDaoModify userDaoModify;

    public UserDaoModify() {
        mDaoManager = GreenDaoManager.getInstance();
    }

    public static UserDaoModify getInstance(){
        if (userDaoModify == null){
            userDaoModify = new UserDaoModify();
        }
        return userDaoModify;
    }

    public UserBeanDao getUserBeanDao(){
        return mDaoManager.getDaoSession().getUserBeanDao();
    }

    /**
     * 插入数据 若未建表则先建表
     * @param userBean
     */
    public boolean insertUserData(UserBean userBean){
       boolean flag = false;
       flag = getUserBeanDao().insert(userBean) == -1;
       return flag;
    }

    /**
     * 插入或替换数据
     * @param userBean
     * @return
     */
    public boolean insertOrReplaceData(UserBean userBean){
        boolean flag = false;
        try {
            flag = getUserBeanDao().insertOrReplace(userBean) == -1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 插入多条数据  子线程完成
     * @param list
     * @return
     */
    public boolean insertOrRelaceMultiData(final List<UserBean> list){
        boolean flag = false;
        try {
            getUserBeanDao().getSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserBean userBean : list){
                        mDaoManager.getDaoSession().insertOrReplace(userBean);
                    }
                }
            });
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新数据
     * @param userBean
     * @return
     */
    public boolean updateUserData(UserBean userBean){
        boolean flag = false;
        try {
            getUserBeanDao().update(userBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据id删除数据
     * @param userBean
     * @return
     */
    public boolean deleteUserData(UserBean userBean){
        boolean flag =false;
        try{
            getUserBeanDao().delete(userBean);
            flag =true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有数据
     * @return
     */
    public boolean deleteAllData(){
        boolean flag =false;
        try{
            getUserBeanDao().deleteAll();
            flag =true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 根据主键查询
     * @param key
     * @return
     */
    public UserBean queryUserDataById(long key){
        return getUserBeanDao().load(key);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<UserBean> queryAllData(){
        return getUserBeanDao().loadAll();
    }


    /**
     * 根据名称查询 以年龄降序排列
     * @param name
     * @return
     */
    public List<UserBean> queryUserByName(String name){
        Query<UserBean> build = null;
        try{
            build =getUserBeanDao().queryBuilder()
                    .where(UserBeanDao.Properties.UserName.eq(name))
                    .orderDesc(UserBeanDao.Properties.Age)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }

        return build.list();
    }


    /**
     * 根据参数查询
     * @param where
     * @param param
     * @return
     */
    public List<UserBean> queryUserByParams(String where, String... param){
        return getUserBeanDao().queryRaw(where,param);
    }


}
