package com.sendi.picture_recognition.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.utils.GlideLoader;
import com.sendi.picture_recognition.view.adapter.UserInfoAdapter;
import com.sendi.picture_recognition.presenter.abstract_act.AbsAlertUserInfoPresenter;
import com.sendi.picture_recognition.presenter.act.AlertUserInfoPresenter;
import com.sendi.picture_recognition.view.activity.abs.AbsUserInfoView;
import com.sendi.userdb.User;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 用户信息界面
 */
public class UserInfoActivity extends AbsUserInfoView {
    private final static String TAG = "SENDI";
    private ListView mListView;
    private ImageView user_pic;
    private ImageButton ib_back;
    private UserInfoAdapter mAdapter;
    private TextView tv_title;
    private ImageConfig mImageConfig;
    private String flag = "1";
    private String genderId;
    private AbsAlertUserInfoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();

        initData();


    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_user_info);

        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("个人信息");
    }

    private void initData() {
        mPresenter = new AlertUserInfoPresenter();
        mPresenter.bindView(this);
        mPresenter.initUser(this);
    }

    @Override
    public void showUserInfo(User user) {
        if (user == null)
            return;

        genderId = user.getGander();
        mAdapter = new UserInfoAdapter(this, user);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout rl = (RelativeLayout) view;
                Intent intent = new Intent(UserInfoActivity.this, AlertUserInfoActivity.class);
                switch (position) {
                    case 0://头像
                        flag = "0";
                        user_pic = (ImageView) rl.getChildAt(2);
                        selectPic();
                        break;
                    case 1://名字
                        flag = "1";
                        break;
                    case 2://志愿者号
                        flag = "2";
                        break;
                    case 3://手机号码
                        flag = "3";
                        break;
                    case 4://性别
                        flag = "4";
                        intent.putExtra("genderId", genderId);
                        break;
                    case 5://爱好
                        flag = "5";
                        break;
                }
                if (flag == "2" || flag == "0") {
                    return;
                }
                intent.putExtra("flag", flag);
                startActivity(intent);
            }
        });
    }

    /**
     * 选择图片
     */
    private void selectPic() {
        mImageConfig = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(R.color.colorTheme)
                .titleBgColor(R.color.colorTheme)
                .titleTextColor(R.color.colorWhite)
                .singleSelect()
                .showCamera()
                .requestCode(GlobalConfig.REQUEST_CODE)
                .build();
        ImageSelector.open(UserInfoActivity.this, mImageConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalConfig.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            List<MultipartBody.Part> parts = null;
            for (final String path : pathList) {
                Log.i(TAG, "onActivityResult: " + path);
                File file = new File(path);
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("vid", GlobalConfig.USERID);
                RequestBody imgBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.addFormDataPart("file", file.getName(), imgBody);
                parts = builder.build().parts();
            }

            mPresenter.alertUerPic(parts);
        }
    }

    @Override
    public void alertUserPic(String path) {
        Glide.with(this)
                .load(path)
                .error(R.mipmap.app_logo)
                .centerCrop()
                .into(user_pic);
        GlobalConfig.USERPID=path;//设置头像
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(boolean isSuccess) {

    }

    @Override
    public void getCodeSuccess() {

    }

    public void back(View view) {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
