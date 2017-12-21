package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.RecordData;


import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class RecordFragmentAdapter extends RecyclerView.Adapter<RecordFragmentAdapter.RecordViewHolder> {
    private List<RecordData> mRecordDataList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public RecordFragmentAdapter(List<RecordData> recordDataList, Context context) {
        mRecordDataList = recordDataList;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.record_item, null);
        RecordViewHolder viewHolder = new RecordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, final int position) {
        Glide.with(mContext).
                load(mRecordDataList.get(position).
                        getImageUrl()).
                into(holder.recordPic);
        String tags = "";
        for (int i = 0; i < mRecordDataList.get(position).getSelectedTags().length; i++) {
            tags = tags + " " + mRecordDataList.get(position).getSelectedTags()[i];
        }
        holder.flags.setText(tags);
        if (mRecordDataList.get(position).getStatus().equals("0")) {
            holder.status.setText("待完成");
        } else {
            holder.status.setText("已完成");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordDataList.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        ImageView recordPic;
        TextView flags;
        TextView status;

        public RecordViewHolder(View itemView) {
            super(itemView);
            recordPic = (ImageView) itemView.findViewById(R.id.iv_record_item);
            flags = (TextView) itemView.findViewById(R.id.tv_record_flag);
            status = (TextView) itemView.findViewById(R.id.tv_record_status);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
