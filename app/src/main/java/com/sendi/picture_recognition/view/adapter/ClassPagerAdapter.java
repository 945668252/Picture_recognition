package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.ClassData;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ClassPagerAdapter extends RecyclerView.Adapter<ClassPagerAdapter.PagerViewHolder> {

    private List<Integer>imgs;
    private List<String>texts;
    private List<ClassData>mPicDatas;
    private List<String>firstTag;
    private OnItemClickListener mListener;
    private Context mContext;

    public ClassPagerAdapter(List<ClassData> picDatas ,Context context) {
        mPicDatas = picDatas;
        mContext = context;
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public PagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext,R.layout.pager_item,null);
        PagerViewHolder viewHolder=new PagerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PagerViewHolder holder, final int position) {
        Log.i("TAG", "onBindViewHolder: "+mPicDatas.get(position).getPath());
        Glide.with(mContext)
                .load(mPicDatas.get(position).getPath())
                .thumbnail(0.1f)//缩略图
                .into(holder.mImageView);
        holder.mTextView.setText(mPicDatas.get(position).getClassify());
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
        void onClick(View view, int position);
    }
}
