package com.liux.easyreadingandroidmvp.view;

import com.liux.easyreadingandroidmvp.view.base.BaseView;
import com.liux.module.wxBean.ArticleSearchBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.view
 * 项目日期：2018/4/8
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public interface FgHomeView extends BaseView {
    void getListDataAdapter(List<ArticleSearchBean.ResultBean.ListBean> resultList);
}
