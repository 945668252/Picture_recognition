package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.SearchPicData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class SearchPagerAdapter extends RecyclerView.Adapter<SearchPagerAdapter.PagerViewHolder> {

    private List<Integer>imgs;
    private List<String>texts;
    private List<SearchPicData>mPicDatas;
    private List<String>firstTag;
    private OnItemClickListener mListener;
    private Context mContext;

    public SearchPagerAdapter(List<SearchPicData> picDatas, List<String>firstTag,Context context) {
        this.firstTag=firstTag;
        mPicDatas = picDatas;
        mContext = context;
    }

    public SearchPagerAdapter(Context mContext) {
        this.mContext=mContext;
        imgs=new ArrayList<>();
        texts=new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            imgs.add(R.mipmap.test);
            texts.add("hobby");
        }
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public PagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.pager_item,null,false);
        PagerViewHolder viewHolder=new PagerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PagerViewHolder holder, final int position) {
        Log.i("TAG", "onBindViewHolder: "+mPicDatas.get(position).getPath());
        Glide.with(mContext)
                .load(mPicDatas.get(position).getPath())
                .thumbnail(0.1f)//先显示缩略图
                .into(holder.mImageView);
        holder.mTextView.setText(firstTag.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPicDatas.size();
    }

    class PagerViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
        public PagerViewHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.pic);
            mTextView= (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener{
        void onClick(View view,int position);
    }
}
