package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.GreenDaoUserAdapter;
import com.liux.easyreadingandroidmvp.base.BaseSwipeBackActivity;
import com.liux.easyreadingandroidmvp.customView.DividerItemDecoration;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.greenDao.UserBean;
import com.liux.easyreadingandroidmvp.greenDao.UserDaoModify;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2018/3/29
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class GreenDaoTestActivity extends BaseSwipeBackActivity {
    @BindView(R.id.btn_Insert)
    Button btnInsert;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_Updata)
    Button btnUpdata;
    @BindView(R.id.btn_Query)
    Button btnQuery;
    @BindView(R.id.rv_UserInfo)
    RecyclerView rvUserInfo;

    private GreenDaoUserAdapter mGreenDaoUserAdapter;
    private List<UserBean> mUserBeanList = new ArrayList<>();

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("greenDao测试");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_greendao_test;
    }

    @Override
    protected void initView() {
        initAdapter();
    }

    @Override
    protected void detachView() {

    }

    private void initAdapter() {
        mGreenDaoUserAdapter = new GreenDaoUserAdapter(R.layout.item_greendao_user_info,mUserBeanList);
        rvUserInfo.setLayoutManager(new LinearLayoutManager(this));
        rvUserInfo.addItemDecoration(new DividerItemDecoration(this,1));
        rvUserInfo.setAdapter(mGreenDaoUserAdapter);
    }


    /**
     * 添加数据
     */
    private void addData(){
        UserBean mUserBean = new UserBean();
        mUserBean.setUserName("张三");
        mUserBean.setPassWord("123123");
        mUserBean.setAge(18);
        mUserBean.setSex("男");
        UserDaoModify.getInstance().insertUserData(mUserBean);
        queryListData();
    }

    private void deleteData(){
        if (!mUserBeanList.isEmpty()){
            UserDaoModify.getInstance().deleteUserData(mUserBeanList.get(0));
            queryListData();
        }
    }

    private void updateData(){
        if (!mUserBeanList.isEmpty()){
            UserBean userBean = mUserBeanList.get(0);
            userBean.setUserName("李四");
            UserDaoModify.getInstance().updateUserData(userBean);
            queryListData();
        }
    }

    private void queryListData() {
        mUserBeanList.clear();
        mUserBeanList.addAll(UserDaoModify.getInstance().queryAllData());
        mGreenDaoUserAdapter.notifyDataSetChanged();
        Toast.makeText(this, "查询到" + mUserBeanList.size() + "条数据", Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.btn_Insert, R.id.btn_delete, R.id.btn_Updata, R.id.btn_Query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Insert:
                addData();
                break;
            case R.id.btn_delete:
                deleteData();
                break;
            case R.id.btn_Updata:
                updateData();
                break;
            case R.id.btn_Query:
                queryListData();
                break;
        }
    }

}
