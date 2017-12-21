package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.HomePicInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ClassDetailAdapter extends RecyclerView.Adapter<ClassDetailAdapter.PagerViewHolder> {

    private List<HomePicInfo>mPicDatas;
    private OnItemClickListener mListener;
    private Context mContext;

    public ClassDetailAdapter(List<HomePicInfo> picDatas , Context context) {
        mPicDatas = picDatas;
        mContext = context;
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public PagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext,R.layout.pic_class_item,null);
        PagerViewHolder viewHolder=new PagerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PagerViewHolder holder, final int position) {
//        Log.i("TAG", "onBindViewHolder: "+mPicDatas.get(position).getPic_url());
        Glide.with(mContext).load(mPicDatas.get(position).getPic_url()).into(holder.mImageView);
//        Glide.with(mContext).load(picList.get(position)).into(holder.mImageView);
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
        public PagerViewHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.pic);
        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
}
