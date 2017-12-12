package com.sendi.picture_recognition.controller.adapter.pk_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.bean.ChallengeHistory;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {

    private Context mContext;
    private List<ChallengeHistory> mHistoryList;
    private OnHistoryItemClickListener mListener;

    public HistoryAdapter(Context context, List<ChallengeHistory> mHistoryList) {
        this.mHistoryList = mHistoryList;
        this.mContext = context;
    }

    public void setListener(OnHistoryItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.history_pk_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.mTextView.setText(mHistoryList.get(position).getResultStr());
        Glide.with(mContext).
                load(mHistoryList.get(position).imgUrl).
//                placeholder(R.mipmap.app_logo).
                into(holder.mImageView);
        String status = mHistoryList.get(position).resultStr;
        if (status.equals("等待")) {
            holder.mTextView.setText("等对对方接受挑战");
            holder.mResultPic.setImageResource(R.mipmap.waiting);
            if (!(GlobalConfig.USERID.equals(mHistoryList.get(position).getChallenger()))){
                holder.mTextView.setText("对方期待你接受挑战");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onCLick(v, position);
                        }
                    }
                });
            }

        } else if (status.equals("未接受")) {
            holder.mResultPic.setImageResource(R.mipmap.refuse);
        } else if (status.equals("已过期")) {
            holder.mResultPic.setImageResource(R.mipmap.out_time);
        } else {
            if (status.equals("胜利")) {
                holder.mResultPic.setImageResource(R.mipmap.win);
            } else if (status.equals("失败")) {
                holder.mResultPic.setImageResource(R.mipmap.lose);
            } else if (status.equals("平局")) {
                holder.mResultPic.setImageResource(R.mipmap.tie);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onCLick(v, position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;
        public ImageView mResultPic;

        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_pk_history);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_pk_pic);
            mResultPic = (ImageView) itemView.findViewById(R.id.iv_status);
        }
    }

    public interface OnHistoryItemClickListener {
        void onCLick(View view, int position);
    }
}
