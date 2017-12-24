package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.config.GlobalConfig;

import com.sendi.picture_recognition.base.BaseFragment;
import com.sendi.picture_recognition.view.fragment.home.HomeFragment;

import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.picture_recognition.view.fragment.home.pk.PkFragment;
import com.sendi.picture_recognition.view.fragment.home.search.SearchFragment;
import com.sendi.userdb.User;
import com.sendi.userdb.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 * 重构
 * 主活动
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "ASENDI";
    private RadioGroup mRadioGroup;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    private TextView tv_user_name;
    private ImageView mMainUserPic;
    private List<BaseFragment> mFragmentList;
    private static int position;
    private Fragment mContent;
    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//初始化View
        initView();
//初始化数据
        initData();

        iniFragment();

        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_user_name.setText(userDao.queryBuilder().listLazy().get(0).getUser_nickname());
        Log.i(TAG, "onResume: " + GlobalConfig.USERPID);
        Glide.with(this)
                .load(GlobalConfig.USERPID)
                .centerCrop()
                .error(R.mipmap.app_logo)
                .into(mMainUserPic);
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_bottom);
        NavigationView navigationView = (NavigationView) findViewById(R.id.anv);

        View view = navigationView.getHeaderView(0);
        mMainUserPic = (ImageView) view.findViewById(R.id.left_user_pic);
        tv_user_name = (TextView) view.findViewById(R.id.uer_name);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        userDao = SqliteDBUtils
                .getInstance(getApplication())
                .getUserDao();
        GlobalConfig.USERID = userDao
                .queryBuilder()
                .listLazy()
                .get(0)
                .getId() + "";
        List<User> list = userDao.queryBuilder().listLazy();
        User user = list.get(0);
        Log.i(TAG, "initData: " + user.getUser_pic_url());
        Log.i(TAG, "initData: " + user.getUser_nickname());
        tv_user_name.setText(user.getUser_nickname());
        GlobalConfig.USERPID = user.getUser_pic_url();//初始化头像
        Glide.with(this)
                .load(GlobalConfig.USERPID)
                .error(R.mipmap.app_logo)
                .centerCrop()
                .into(mMainUserPic);
    }

    private void setListener() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home://首页
                        position = 0;
                        break;
                    case R.id.rb_search://喜欢
                        position = 1;
                        break;
                    case R.id.rb_record://记录
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
        //设置默认选中首页
        mRadioGroup.check(R.id.rb_home);

    }

    /**
     * @param from 刚刚显示的Fragment
     * @param to   先要显示的Fragment
     *             为了让一个对象只初始化一次
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager fm = getSupportFragmentManager();
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
                    ft.add(R.id.fl_content, to).commit();
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
        return mFragmentList.get(position);
    }

    private void iniFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new SearchFragment());
        mFragmentList.add(new PkFragment());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_grade) {//积分
            startActivity(new Intent(MainActivity.this, GradeActivity.class));
        } else if (id == R.id.nav_alert) {//修改资料
            startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
        } else if (id == R.id.nav_record) {//个人记录
            startActivity(new Intent(MainActivity.this, RecordActivity.class));
        } else if (id == R.id.nav_setting) {//设置
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        } else if (id == R.id.nav_goods) {//商城
            startActivity(new Intent(MainActivity.this, IntegralShopActivity.class));
        }
        return true;
    }
}
