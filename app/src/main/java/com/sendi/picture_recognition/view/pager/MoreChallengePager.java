package com.sendi.picture_recognition.view.pager;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.view.adapter.ChallengeTagAdapter;
import com.sendi.picture_recognition.bean.HomePicInfo;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class MoreChallengePager  {

    private Context mContext;
    private String[] mTagArray;
    private List<String>selectedTags;
    private HomePicInfo picInfo;
    public  View mRootView;
    private OnSaveItemClickListener mListener;
    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private ChallengeTagAdapter mAdapter;


    public MoreChallengePager(Context context, HomePicInfo picInfo) {
        this.mContext=context;
        this.picInfo=picInfo;
        mRootView=initView();
    }

    public void setSaveListener(OnSaveItemClickListener listener){
        this.mListener=listener;
    }

    protected View initView() {
        View view=View.inflate(mContext, R.layout.challenge_pager_layout,null);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rv_challenge_tags);
        mImageView= (ImageView) view.findViewById(R.id.iv_challenge_pic);
        return view;
    }

    public void initData(){
        //首先要先初始化历史选择的标签
        selectedTags=new ArrayList<>();

        Glide.with(mContext)
                .load(picInfo.getPic_url())
                .into(mImageView);
        mTagArray= SplitString.StringToArray(picInfo.getTags());
        mAdapter=new ChallengeTagAdapter(mTagArray,mContext);
        GridLayoutManager manager=new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new ChallengeTagAdapter.OnTagClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView textView = (TextView) view;
                if (selectedTags.contains(mTagArray[position])) {
                    selectedTags.remove(mTagArray[position]);
                    textView.setBackgroundResource(R.drawable.make_tag_item_bg);
                    textView.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                } else {
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorTheme));
                    textView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    selectedTags.add(mTagArray[position]);
                }
                if (mListener!=null){
                    mListener.onSave(view,position,selectedTags,picInfo.getPic_id());
                    Log.i("MoreChallenge", "onClick: "+position);
                }
            }
        });
    }

    public interface OnSaveItemClickListener{
        void onSave(View view, int position, List<String> selectedTags, String pId);
    }
}
