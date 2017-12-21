package com.sendi.picture_recognition.view.fragment.home.pk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseFragment;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 * 记录
 */

public class PkFragment extends BaseFragment {
    private final static String TAG = PkFragment.class.getSimpleName();
    private RadioGroup mRadioGroup;
    private List<BaseFragment> fragmentList;
    private static int position;
    private Fragment mContent;

    public PkFragment(){}

    @Override
    public View initView() {
        Log.i(TAG, "initView: PK页被初始化了");
        View view = View.inflate(mContext, R.layout.pk_fragment_layout, null);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_top_pk);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.i(TAG, "initData: record");
        fragmentList = new ArrayList<>(3);
        fragmentList.add(new ChartFragment());
        fragmentList.add(new HistoryFragment());
        fragmentList.add(new MoreFragment());



        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_pk_charts:
                        position = 0;
                        break;
                    case R.id.rb_pk_history:
                        position = 1;
                        break;
                    case R.id.rb_pk_more:
                        position = 2;
                        break;
                    default:
                        position = 0;
                        break;
                }

                //根据位置得到对应的Fragment
                BaseFragment fragment = getFragment(position);
                //替换
                switchFragment(mContent, fragment);
            }
        });
        mRadioGroup.check(R.id.rb_pk_charts);//默认是排行榜

    }

    private void switchFragment(Fragment from, Fragment to)  {
        if (from != to) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mContent = to;
            //执行切换
            //判断是否有被添加
            if (!to.isAdded()) {
                //没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                // 添加to
                if (to != null) {
                    ft.add(R.id.fl_pk, to).commit();
                }

            } else {
                //已被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //to显示
                if (to != null)
                    ft.show(to).commit();
            }
        }
    }

    private BaseFragment getFragment(int position) {
            return fragmentList.get(position);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }

}
