package com.sendi.picture_recognition.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.GoodsData;
import com.sendi.picture_recognition.view.adapter.SearchPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder> {

    private Context mContext;
    private List<GoodsData>mGoodsDataList;
    private SearchPagerAdapter.OnItemClickListener mListener;

    public GoodsAdapter(Context context){
        this.mContext=context;
    }

    public GoodsAdapter(Context context, List<GoodsData> goodsDataList) {
        mContext = context;
        mGoodsDataList = goodsDataList;
    }

    public void setGoodsDataList(List<GoodsData> goodsDataList) {
        mGoodsDataList = goodsDataList;
    }

    public void setListener(SearchPagerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.integral_goods_item,parent,false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, final int position) {
        Glide.with(mContext).load(mGoodsDataList.get(position).getPath()).
                placeholder(R.mipmap.app_logo).
                into(holder.mImageView);
        Log.i("TAG", "onBindViewHolder: "+mGoodsDataList.get(position).getIntegration());
        holder.goodsIntegral.setText(mGoodsDataList.get(position).getIntegration());
        holder.goodsName.setText(mGoodsDataList.get(position).getName());

        holder.mButton.setOnClickListener(new View.OnClickListener() {
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
        return mGoodsDataList.size();
    }

    public class GoodsHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView goodsName;
        public TextView goodsIntegral;
        public Button mButton;

        public GoodsHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_goods_pic);
            goodsName= (TextView) itemView.findViewById(R.id.tv_goods_name);
            goodsIntegral= (TextView) itemView.findViewById(R.id.tv_integral);

            mButton= (Button) itemView.findViewById(R.id.bt_buy);
        }
    }

    interface OnItemListener{
        void onClick(View view,int position);
    }
}
