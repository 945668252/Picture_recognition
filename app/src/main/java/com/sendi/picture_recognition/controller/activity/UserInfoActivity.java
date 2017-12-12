package com.sendi.picture_recognition.controller.activity;

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
import com.sendi.picture_recognition.controller.GlideLoader;
import com.sendi.picture_recognition.controller.adapter.UserInfoAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.userdb.UserDao;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//修改个人信息
public class UserInfoActivity extends BaseActivity {
    private final static String TAG = "SENDI";
    private ListView mListView;
    private ImageView user_pic;
    private ImageView user_main_pic;
    private ImageButton ib_back;
    private UserInfoAdapter mAdapter;
    private TextView tv_title;
    private ImageConfig mImageConfig;
    private String flag = "1";
    private String genderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();


        initData();


    }

    private void initData() {
        //从数据库获取用户信息
        UserDao userDao = SqliteDBUtils
                .getInstance(this)
                .getUserDao();
        List<com.sendi.userdb.User> list = userDao.queryBuilder().listLazy();
        com.sendi.userdb.User user = list.get(0);
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

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_user_info);

        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("个人信息");
    }

    public void back(View view) {
        this.finish();
    }

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
            for (final String path : pathList) {
                Log.i(TAG, "onActivityResult: " + path);
                File file=new File(path);
                MultipartBody.Builder builder=new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("vid",GlobalConfig.USERID);
                RequestBody imgBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                builder.addFormDataPart("file",file.getName(),imgBody);
                List<MultipartBody.Part>parts=builder.build().parts();
                Log.i(TAG, "onActivityResult: path:"+path+" parts:"+parts);
                RetrofitFactory
                        .getInstance()
                        .getAPI()
                        .alertUerPic(parts)
                        .compose(this.<BaseEntity<String>>setThread())
                        .subscribe(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseEntity<String> t) throws Exception {
                                Log.i(TAG, "onSuccess: "+t.toString());
                                Glide.with(UserInfoActivity.this).load(path).into(user_pic);
                                GlobalConfig.USERPID=t.getData();//设置头像
                                UserDao userDao=SqliteDBUtils
                                        .getInstance(UserInfoActivity.this)
                                        .getUserDao();
                               com.sendi.userdb.User user= userDao
                                        .queryBuilder()
                                        .listLazy()
                                        .get(0);
                                Log.i(TAG, "onSuccess: userPicUrl"+user.getUser_pic_url());
                                user.setUser_pic_url(t.getData());
                                userDao.update(user);
                            }

                            @Override
                            protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
