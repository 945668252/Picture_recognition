package com.sendi.picture_recognition.view.fragment.home.pk;

import com.sendi.picture_recognition.base.BaseFragment;
import com.sendi.picture_recognition.controller.LoadDataScrollController;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class AbstractPkFragment<T> extends BaseFragment implements LoadDataScrollController.OnRecyclerRefreshListener{
    public LoadDataScrollController mController;
    //加载数据
    public abstract void showData(List<T> data);

    @Override
    protected void initData() {
        super.initData();
        mController= new LoadDataScrollController(this);
    }
    //创建挑战
    public void createPk(String result,boolean isSuccess){}
}
