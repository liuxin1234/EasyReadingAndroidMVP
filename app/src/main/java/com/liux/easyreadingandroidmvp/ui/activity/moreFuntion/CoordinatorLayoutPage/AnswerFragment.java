package com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage;

import android.os.Bundle;

import com.liux.easyreadingandroidmvp.R;
import com.liux.easyreadingandroidmvp.base.BaseFragment;
import com.orhanobut.logger.Logger;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.ui.activity.moreFuntion.CoordinatorLayoutPage
 * 项目日期：2019/1/9
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class AnswerFragment extends BaseFragment {

    private static final String key = "Answer";

    public static AnswerFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(key,title);
        AnswerFragment answerFragment = new AnswerFragment();
        answerFragment.setArguments(bundle);
        return answerFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_answer;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void loadData() {
        Logger.e("AnswerFragment——loadData");
    }

    @Override
    protected void detachView() {

    }
}
