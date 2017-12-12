package com.sendi.picture_recognition.controller.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canyinghao.candialog.CanDialog;
import com.canyinghao.candialog.CanDialogInterface;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.controller.adapter.TagAdapter;
import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.base.BaseObserver;
import com.sendi.picture_recognition.utils.httputils.RetrofitFactory;
import com.sendi.picture_recognition.widget.popupwindow.ComonPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改记录
 */
public class UpdateTagsActivity extends BaseActivity implements ComonPopupWindow.ViewInterface {
    private final String TAG = "SENDI";
    private RecyclerView rv_tags;
    private RecyclerView rv_record;
    private ImageView iv_pic;
    private List<String> selectedTagList;
    private List<String> unSelectedTagList;
    private List<String> tagsList;
    private TagAdapter unSelectedAdapter;
    private TagAdapter selectedAdapter;
    private RecordData mRecordData;
    private ComonPopupWindow mPopupWindow;
    private String selectedTag;
    private String editTag;
    private View contentDialogView;
    private CanDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tags);
        //图片与通知栏融为一体
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initData();
    }

    private void initData() {
        mRecordData = getIntent().getParcelableExtra("recordData");
        selectedTagList = new ArrayList<>();
        unSelectedTagList = new ArrayList<>();
        tagsList = new ArrayList<>();
        Log.i(TAG, "initData: " + mRecordData.getImageUrl());
        Glide.with(this)
                .load(mRecordData.getImageUrl())
                .into(iv_pic);
        for (int i = 0; i < mRecordData.getSelectedTags().length; i++) {
            if (mRecordData.getSelectedTags()[i] != null)
                selectedTagList.add(mRecordData.getSelectedTags()[i]);
        }
        tagsList = selectedTagList;//记录最开始的历史标签
        for (int i = 0; i < mRecordData.getUnselectedTags().length; i++) {
            if (mRecordData.getUnselectedTags()[i] != null)
                unSelectedTagList.add(mRecordData.getUnselectedTags()[i]);
        }
        unSelectedAdapter = new TagAdapter(unSelectedTagList, this);//系统推送的
        selectedAdapter = new TagAdapter(selectedTagList, this);//历史记录


        rv_tags.setLayoutManager(new GridLayoutManager(this, 4));
        rv_tags.setItemAnimator(new DefaultItemAnimator());
        rv_tags.setAdapter(unSelectedAdapter);

        unSelectedAdapter.setListener(new TagAdapter.OnClickListener() {
            //系统推送标签
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, "onClick: " + selectedTagList);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.i("TAG", "onLongClick: " + position + "长按");
                selectedTag = mRecordData.getUnselectedTags()[position];

                if (mPopupWindow != null && mPopupWindow.isShowing()) return;
                mPopupWindow = new ComonPopupWindow.Builder(UpdateTagsActivity.this)
                        .setView(R.layout.popupwindow_add_layout)
                        .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setViewOnclickListener(UpdateTagsActivity.this)
                        .setOutsideTouchable(true)
                        .create(position);
                mPopupWindow.showAsDropDown(view);
            }

            @Override
            public void onAddClick(int position) {

            }
        });

        rv_record.setLayoutManager(new GridLayoutManager(this, 4));
        rv_record.setItemAnimator(new DefaultItemAnimator());
        rv_record.setAdapter(selectedAdapter);
        selectedAdapter.addView(true, selectedTagList);

        selectedAdapter.setListener(new TagAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                //历史记录标签
//                String tag=mRecordData.getSelectedTags()[position];
//                TextView textView= (TextView) view;
//                if (selectedTagList.contains(tag)){
//                    selectedTagList.remove(tag);
//                    textView.setBackgroundResource(R.drawable.make_tag_item_bg);
//                    textView.setTextColor(getResources().getColor(R.color.colorBlack));
//                }else {
//                    selectedTagList.add(tag);
//                    textView.setBackgroundColor(getResources().getColor(R.color.colorTheme));
//                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
//                }
//
//                Log.i(TAG, "onClick: "+selectedTagList);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.i(TAG, "onClick: " + selectedTagList.get(position));
                if (mPopupWindow != null && mPopupWindow.isShowing()) return;
                mPopupWindow = new ComonPopupWindow.Builder(UpdateTagsActivity.this)
                        .setView(R.layout.popupwindow_down_layout)
                        .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setViewOnclickListener(UpdateTagsActivity.this)
                        .setOutsideTouchable(true)
                        .create(position);
                mPopupWindow.showAsDropDown(view);
            }

            @Override
            public void onAddClick(int position) {
                //弹出输入标签的窗口，然后提交给服务端
                showEditDialog(contentDialogView, "添加标签", "添加成功", null, true, position);
                Log.i(TAG, "onClick: " + dialog.getChildCount());
                Log.i(TAG, "onAddClick: " + selectedTagList.size());
                Log.i(TAG, "onAddClick: " + selectedTagList);
            }
        });
    }

    private void initView() {
        contentDialogView = View.inflate(UpdateTagsActivity.this, R.layout.edit_tag_dialog_layout, null);
        iv_pic = (ImageView) findViewById(R.id.iv_make_tag);
        rv_record = (RecyclerView) findViewById(R.id.rv_record);
        rv_tags = (RecyclerView) findViewById(R.id.rv_tabs);
    }

    @Override
    public void getChildView(View view, int layoutResId, final int position) {
        switch (layoutResId) {
            case R.layout.popupwindow_down_layout:
                final TextView tv_alert = (TextView) view.findViewById(R.id.tv_alert);
                final TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
                tv_alert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //弹出修改窗口
//                        Toast.makeText(UpdateTagsActivity.this, "修改标签", Toast.LENGTH_SHORT).show();
                        showEditDialog(contentDialogView, "修改标签", "修改成功",
                                selectedTagList.get(position), false, position);
                        mPopupWindow.dismiss();
                    }
                });
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "onClick: delete"+selectedTagList.get(position));
                        //向服务器提交删除的数据
                        RetrofitFactory
                                .getInstance()
                                .getAPI()
                                .deleteTag(GlobalConfig.USERID, mRecordData.getPid(),
                                        selectedTagList.get(position))
                                .compose(UpdateTagsActivity.this.<BaseEntity<String>>setThread())
                                .subscribe(new BaseObserver<String>() {
                                    @Override
                                    protected void onSuccess(BaseEntity<String> t) throws Exception {
                                        selectedTagList.remove(selectedTagList.get(position));
                                        selectedAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                                    }
                                });
                        mPopupWindow.dismiss();
                    }
                });
                break;

            case R.layout.popupwindow_add_layout:
                TextView tv_add = (TextView) view.findViewById(R.id.tv_add);
                tv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedTagList.contains(selectedTag)) {
                            Toast.makeText(UpdateTagsActivity.this, "您已添加过该标签", Toast.LENGTH_SHORT).show();
                            mPopupWindow.dismiss();
                        } else {
                            //向服务器提交选择要添加的标签
                            RetrofitFactory
                                    .getInstance()
                                    .getAPI()
                                    .addCommonTag(GlobalConfig.USERID, mRecordData.getPid(), selectedTag)
                                    .compose(UpdateTagsActivity.this.<BaseEntity<String>>setThread())
                                    .subscribe(new BaseObserver<String>() {
                                        @Override
                                        protected void onSuccess(BaseEntity<String> t) throws Exception {
                                            Toast.makeText(UpdateTagsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                            selectedTagList.set(selectedTagList.size() - 1, selectedTag);
                                            selectedAdapter.addData(true, selectedTagList);
                                        }

                                        @Override
                                        protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                                        }
                                    });

                            mPopupWindow.dismiss();
                        }
                    }
                });
        }
    }

    /**
     * 弹窗
     */
    private void showEditDialog(final View contentView, String title,
                                final String successText, String hintText,
                                final boolean isAdd, final int position) {
        final EditText et_tag = (EditText) contentView.findViewById(R.id.et_tag_dialog);
        et_tag.setText("");
        if (!isAdd) {
            //如果不是添加，则把要修改的标签写到输入框中
            et_tag.setText(hintText);
        }
        final TextView tv_title = (TextView) contentView.findViewById(R.id.tv_dialog_title);
        final ImageView iv_smail = (ImageView) contentView.findViewById(R.id.iv_add_smail);
        if (iv_smail.getVisibility() == View.VISIBLE) {
            et_tag.setVisibility(View.VISIBLE);
            iv_smail.setVisibility(View.GONE);
        }
        tv_title.setText(title);
        //获得mCustom，移除子View
        if (contentView.getParent() != null) {
            ((FrameLayout) contentView.getParent()).removeView(contentView);
        }
        dialog = new CanDialog.Builder(this)
                .setView(contentView)
                .setNegativeButton("取消", true, null)
                .setPositiveButton("提交", false, new CanDialogInterface.OnClickListener() {
                    @Override
                    public void onClick(CanDialog dialog, int checkItem,
                                        CharSequence text, boolean[] checkItems) {
                        editTag = et_tag.getText().toString();
                        Log.i(TAG, "onClick: " + text);
                        //向服务器提交标签
                        //向历史标签添加
                        //笑脸显示，输入框隐藏
                        if (isAdd) {
                            //提交添加的标签
                            RetrofitFactory
                                    .getInstance()
                                    .getAPI()
                                    .addEditTag(GlobalConfig.USERID, mRecordData.getPid(), editTag)
                                    .compose(UpdateTagsActivity.this.<BaseEntity<String>>setThread())
                                    .subscribe(new BaseObserver<String>() {
                                        @Override
                                        protected void onSuccess(BaseEntity<String> t) throws Exception {
                                            Toast.makeText(UpdateTagsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                            tv_title.setText(t.getData());
                                            selectedTagList.set(selectedTagList.size() - 1, editTag);
                                            selectedAdapter.addData(true, selectedTagList);
                                        }

                                        @Override
                                        protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                                        }

                                        @Override
                                        protected void onCodeError(BaseEntity<String> t) throws Exception {
                                            super.onCodeError(t);
                                            tv_title.setText(t.getData());
                                        }
                                    });
                        } else {
                            //提交新的标签和历史的标签
                            RetrofitFactory
                                    .getInstance()
                                    .getAPI()
                                    .alertTag(GlobalConfig.USERID, mRecordData.getPid(),
                                            selectedTagList.get(position) ,editTag )
                                    .compose(UpdateTagsActivity.this.<BaseEntity<String>>setThread())
                                    .subscribe(new BaseObserver<String>() {
                                        @Override
                                        protected void onSuccess(BaseEntity<String> t) throws Exception {
                                            Toast.makeText(UpdateTagsActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            selectedTagList.set(position, editTag);
                                            selectedAdapter.notifyDataSetChanged();
                                            tv_title.setText(successText);
                                        }

                                        @Override
                                        protected void onConnectFailure(Throwable e, boolean isNetWorkError) throws Exception {

                                        }

                                        @Override
                                        protected void onCodeError(BaseEntity<String> t) throws Exception {
                                            super.onCodeError(t);
                                            tv_title.setText(t.getData());
                                        }
                                    });
                        }
                        iv_smail.setVisibility(View.VISIBLE);
                        et_tag.setVisibility(View.GONE);


                        dialog.setPositiveButton("确认", true, null);
                        dialog.setNegativeButton("", true, null);
                    }
                })
                .setCircularRevealAnimator(CanDialog.CircularRevealStatus.BOTTOM_RIGHT)
                .setCancelable(true)
                .create();
        dialog.show();
    }

    public void back(View view) {
        UpdateTagsActivity.this.finish();
    }

}
