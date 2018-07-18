package com.liux.easyreadingandroidmvp.ui;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.adapter.LeftItemAdapter;
import com.liux.easyreadingandroidmvp.base.BaseActivity;
import com.liux.easyreadingandroidmvp.common.db.ACache;
import com.liux.easyreadingandroidmvp.customView.ToolbarHelper;
import com.liux.easyreadingandroidmvp.eventBus.LoginEvent;
import com.liux.easyreadingandroidmvp.ui.activity.FunctionActivity;
import com.liux.easyreadingandroidmvp.ui.activity.LoginActivity;
import com.liux.easyreadingandroidmvp.utils.MenuDataUtils;
import com.liux.easyreadingandroidmvp.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity  {

    @BindView(R.id.lv_left_main)
    ListView lvLeftMain;
    @BindView(R.id.top_bar_icon)
    ImageView topBarIcon;
    @BindView(R.id.drag_layout)
    DragLayout dragLayout;
    @BindView(R.id.top_bar_search_btn)
    Button topBarSearchBtn;
    @BindView(R.id.tv_userName)
    TextView tvUserName;

    private ACache mACache;


    public DragLayout getDragLayout() {
        return dragLayout;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //禁止向右侧滑返回
        setSwipeBackEnable(false);
        EventBus.getDefault().register(this);
        mACache = ACache.get(this);
        String userName = mACache.getAsString("userName");
        if (userName != null) {
            tvUserName.setText(userName);
        }
        setStatusBar();
        initValiData();
        initListener();
    }


    private void initListener() {
        dragLayout.setDragListener(new CustomDragListener());
    }

    private void initValiData() {
        lvLeftMain.setAdapter(new LeftItemAdapter());
        lvLeftMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了：" + MenuDataUtils.getItemMenus().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                if ("退出登录".equals(MenuDataUtils.getItemMenus().get(position).getTitle())) {
                    mACache.put("userName","");
                    mACache.put("passWord","");
                    mACache.put("token","");
                    mACache.put("uid","");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    /**
     *    NAIN UI主线程
     *   BACKGROUND 后台线程
     *   POSTING 和发布者处在同一个线程
     *   ASYNC 异步线程
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventMainThread(LoginEvent loginEvent){
        tvUserName.setText(loginEvent.getUserName());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.top_bar_icon, R.id.top_bar_search_btn})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.top_bar_icon:
                dragLayout.open();
                break;
            case R.id.top_bar_search_btn:
                intent.setClass(MainActivity.this, FunctionActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private class CustomDragListener implements DragLayout.DragListener {
        /**
         * 界面打开
         */
        @Override
        public void onOpen() {

        }

        /**
         * 界面关闭
         */
        @Override
        public void onClose() {

        }

        /**
         * 界面进行滑动
         * @param percent
         */
        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(topBarIcon, 1 - percent);
        }
    }



}
