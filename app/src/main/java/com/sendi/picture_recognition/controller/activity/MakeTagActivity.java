package com.sendi.picture_recognition.controller.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canyinghao.candialog.CanDialog;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.TagAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.ImgInfo;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.utils.httputils.displayutils.DisplayMetricsUtils;
import com.sendi.picture_recognition.utils.httputils.parseutils.ParseVoiceDataUtils;

import java.util.ArrayList;
import java.util.List;

//打标签
public class MakeTagActivity extends BaseActivity {
    private final String TAG = this.getClass().getName();
    private RecyclerView rv_tags;
    private ImageView mImageView;
    private EditText et_tags;
    private TagAdapter mAdapter;
    private List<String> tagList;
    private List<String> selectedTagList;//用户选择的标签
    private StringBuffer sb;
    private ImgInfo imgInfo;
    private Dialog dialog;
    private int dialogWidth;
    private int dialogHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_tag);
        dialogWidth= DisplayMetricsUtils.getWidth(this,0.8f);
        dialogHeight= DisplayMetricsUtils.getHeight(this,0.3f);
        //图片与通知栏融为一体
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        imgInfo = getIntent().getParcelableExtra("imgInfo");
//        Log.i(TAG, "onCreate: " + imgInfo.toString());
//        Log.i("Length", "initData: " + imgInfo.getTags().length);
        initView();
        initData();
    }

    private void initView() {
        rv_tags = (RecyclerView) findViewById(R.id.rv_tabs);
        mImageView = (ImageView) findViewById(R.id.iv_make_tag);
        et_tags = (EditText) findViewById(R.id.et_tags);
    }

    private void initData() {
        tagList = new ArrayList<>();
        selectedTagList = new ArrayList<>();
        sb=new StringBuffer();
        Log.i(TAG, "initData: "+imgInfo.getUrl());
        //加载图片
        Glide.with(this)
                .load(imgInfo.getUrl())
//                .thumbnail(0.1f)//缩略图
                .into(mImageView);

        for (int i = 0; i < imgInfo.getTags().length; i++) {
            tagList.add(imgInfo.getTags()[i]);
        }
        Log.i("Length", "initData: " + imgInfo.getTags().length);

        mAdapter = new TagAdapter(tagList, this);
        rv_tags.setLayoutManager(new GridLayoutManager(this, 4));
        rv_tags.setItemAnimator(new DefaultItemAnimator());
        rv_tags.setAdapter(mAdapter);
        mAdapter.setListener(new TagAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                //更换字体颜色跟背景
                TextView textView = (TextView) view;
                if (selectedTagList.contains(tagList.get(position))) {
                    selectedTagList.remove(tagList.get(position));
                    textView.setBackgroundResource(R.drawable.make_tag_item_bg);
                    textView.setTextColor(getResources().getColor(R.color.colorBlack));
                } else {
                    textView.setBackgroundColor(getResources().getColor(R.color.colorTheme));
                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
                    selectedTagList.add(tagList.get(position));
                }
                Log.i(TAG, "onClick: " + selectedTagList.toString());
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.i("TAG", "onLongClick: " + position + "长按");
            }

            @Override
            public void onAddClick(int position) {

            }
        });
    }

    public void showDialog() {
        dialog=new Dialog(this);
        dialog.setCancelable(false);
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(dialogWidth,dialogHeight);
        View dialogView = View.inflate(MakeTagActivity.this, R.layout.make_tag_success_dialog, null);
        dialog.setContentView(dialogView,lp);
       dialog.show();
    }
    /**
     * 提交标签
     *
     * @param view
     */
    public void submitTag(View view) {
        //获取用户id
        //获取用户自定义标签
        String tags = et_tags.getText().toString();
        //获取选中的标签
        for (String tag:selectedTagList) {
            tags=tag+" "+tags;
        }
        Log.i(TAG, "submitTag: "+tags);
        tags.replace("？"," ").replace("！","");
        //获取图片的id
        RetrofitFactory
                .getInstance()
                .getAPI()
                .submitTags(GlobalConfig.USERID,imgInfo.getPic_id(),tags)
                .compose(this.<BaseEntity<String>>setThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                        Log.i(TAG, "onSuccess: "+t.getData());
                        showDialog();//展示对话框
                    }

                    @Override
                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onCodeError(BaseEntity<String> t) throws Exception {
                        super.onCodeError(t);
                        Toast.makeText(MakeTagActivity.this, t.getData().toString(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onCodeError: ");
                        showErrorDialog();
                    }
                });
    }

    /**
     * 出错弹窗
     */
    private void showErrorDialog() {
        new CanDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("数据出错啦！")
                .create()
                .show();
    }

    /**
     * 返回
     * 执行finish()方法
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    public void backMain(View view) {
        dialog.dismiss();
        finish();
    }

    public void startListen(View view) {
        RecognizerDialog iatDialog=new RecognizerDialog(this,initListener);
        iatDialog.setParameter(SpeechConstant.DOMAIN, "iat");
        iatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_ch");//中文
        iatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//普通话

        sb.delete(0,sb.length());
        iatDialog.setListener(dialogListener);
        iatDialog.show();
    }

    private InitListener initListener=new InitListener() {
        @Override
        public void onInit(int i) {

        }
    };

    private RecognizerDialogListener dialogListener=new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult result, boolean isLast) {
            Log.i("SENDI", "onResult: "+result.getResultString());
            Log.i("SENDI", "onResult: final"+isLast);

            String text= ParseVoiceDataUtils.getWord(result.getResultString());
            if (!isLast){
                sb.append(" ").append(text);
            }else {
                String etTag=et_tags.getText().toString();
                sb.append(" ").append(text);
                //如果用户提前有填写标签，则将标签放到最后
                if (TextUtils.isEmpty(etTag)||etTag.equals("")){
                    sb.append(etTag);
                }
                String finalResult=sb.toString().replace(","," ").replace("。"," ").replace("，"," ");

                Log.i("ASENDI", "onResult: "+finalResult);

                et_tags.setText(finalResult);
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };
}
