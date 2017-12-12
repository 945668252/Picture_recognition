package com.sendi.picture_recognition.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.ClassData;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.PagerViewHolder> {
    private List<Integer> imgs;
    private List<String> texts;
    private List<ClassData> mPicDatas;
    private List<String> firstTag;
    private TestAdapter.OnItemClickListener mListener;
    private Context mContext;

    public TestAdapter(List<String> texts, List<Integer> imgs, Context context) {
        this.texts = texts;
        this.imgs = imgs;
        Log.i("TAG", "TestAdapter: "+texts);
        Log.i("TAG", "TestAdapter: "+imgs);
        mContext = context;
    }

    public void setListener(TestAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public TestAdapter.PagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.pager_item, null);
        TestAdapter.PagerViewHolder viewHolder = new TestAdapter.PagerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TestAdapter.PagerViewHolder holder, final int position) {
        Log.i("TAG", "onBindViewHolder: " + texts.get(position));
        holder.mImageView.setImageResource(imgs.get(position));
//        Glide.with(mContext)
//                .load(imgs.get(position))
////                .thumbnail(0.1f)//缩略图
//                .into(holder.mImageView);
        holder.mTextView.setText(texts.get(position));
//        holder.mTextView.setText(mPicDatas.get(position).getClassify());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    class PagerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public PagerViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.pic);
            mTextView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
