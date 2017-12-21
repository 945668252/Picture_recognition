package com.sendi.picture_recognition.view.adapter.pk_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.MorePKInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class MorePKAdapter extends RecyclerView.Adapter<MorePKAdapter.MyHolder> {

    private Context mContext;
    private List<MorePKInfo>mList;
    private AdapterItemListener mListener;

    public MorePKAdapter(Context context, List<MorePKInfo>list){
        this.mContext=context;
        this.mList=list;
    }

    public void setListener(AdapterItemListener listener){
        this.mListener=listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(mContext)
                .inflate(R.layout.more_pk_item,parent,false);
        Log.i("TAG", "onCreateViewHolder: ");
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.count.setText(mList.get(position).getValue());

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
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView count;
        public MyHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_pk_pic);
            count= (TextView) itemView.findViewById(R.id.tv_pk_count);
        }
    }

    public interface AdapterItemListener{
        void onClick(View view,int position);
    }
}
