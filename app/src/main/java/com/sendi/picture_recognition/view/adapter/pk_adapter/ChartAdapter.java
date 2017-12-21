package com.sendi.picture_recognition.view.adapter.pk_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.bean.UserChartInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.MyHolder> {

    private Context mContext;
    private List<UserChartInfo> mList;
    private AdapterItemListener mListener;

    public ChartAdapter(Context context, List<UserChartInfo> list){
        this.mList=list;
        this.mContext=context;
    }

    public void setListener(AdapterItemListener listener){
        this.mListener=listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.chart_pk_item,parent,false);
            return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.mName.setText(mList.get(position).getNickname());
        holder.mExperience.setText(mList.get(position).getWeight());
        Glide.with(mContext).
                load(mList.get(position).getPortrait()).
//                placeholder(R.mipmap.app_logo).
                into(holder.mImageView);
        if (mList.get(position).getId().equals(GlobalConfig.USERID)){
            holder.cButton.setVisibility(View.INVISIBLE);
        }
        holder.cButton.setOnClickListener(new View.OnClickListener() {
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
        public TextView mName;
        public TextView mExperience;
        public Button cButton;
        public MyHolder(View itemView) {
            super(itemView);
            mName= (TextView) itemView.findViewById(R.id.tv_chart_name);
            mExperience= (TextView) itemView.findViewById(R.id.tv_experience);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_chart_pic);
            cButton= (Button) itemView.findViewById(R.id.bt_chart);
        }
    }

   public interface AdapterItemListener{
        void onClick(View view,int position);
    }
}
