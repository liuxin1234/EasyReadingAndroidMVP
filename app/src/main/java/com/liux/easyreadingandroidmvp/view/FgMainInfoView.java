package com.liux.easyreadingandroidmvp.view;

import com.liux.easyreadingandroidmvp.view.base.BaseView;
import com.liux.module.wxBean.CategoriesBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.view
 * 项目日期：2018/4/4
 * 作者：liux
 * 功能：只负责MainActivty中的UI逻辑接口方法
 *
 * @author 750954283(qq)
 */

public interface FgMainInfoView extends BaseView {

    void getListDataAdapter(List<CategoriesBean.ResultBean> resultBeanList);
}
