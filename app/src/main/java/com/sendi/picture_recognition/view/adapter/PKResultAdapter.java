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
import com.sendi.picture_recognition.bean.PKResultData;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class PKResultAdapter extends RecyclerView.Adapter<PKResultAdapter.MyHolder> {
    private final String TAG=this.getClass().getName();
private Context mContext;
private List<PKResultData.ImgAndGrade>mImgAndGradeList;

    public PKResultAdapter(Context context, List<PKResultData.ImgAndGrade> imgAndGradeList) {
        mContext = context;
        mImgAndGradeList = imgAndGradeList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(mContext)
                .inflate(R.layout.pk_result_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.myGrade.setText(mImgAndGradeList.get(position).getMyGrade());
        holder.otherGrade.setText(mImgAndGradeList.get(position).getOtherGrade());
        Log.i(TAG, "onBindViewHolder: "+mImgAndGradeList.get(position).getPicUrl());
        Glide.with(mContext)
                .load(mImgAndGradeList.get(position).getPicUrl())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImgAndGradeList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public TextView myGrade;
        public TextView otherGrade;
        public ImageView mImageView;
        public MyHolder(View itemView) {
            super(itemView);
            myGrade= (TextView) itemView.findViewById(R.id.tv_my_grade);
            otherGrade= (TextView) itemView.findViewById(R.id.tv_other_grade);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_history);
        }
    }
}
