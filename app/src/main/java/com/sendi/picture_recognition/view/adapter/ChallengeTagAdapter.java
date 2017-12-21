package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sendi.picture_recognition.R;

/**
 * Created by Administrator on 2017/5/7.
 */

public class ChallengeTagAdapter extends RecyclerView.Adapter<ChallengeTagAdapter.TagViewHolder> {

//    private final int NORMAL_TYPE=1;
//    private final int ADD_TYPE=2;
    private String[] tagList;
    private Context mContext;
    private OnTagClickListener mListener;
    public ChallengeTagAdapter(String[] tagList, Context context) {
        this.tagList = tagList;
        mContext = context;
    }

    public void setListener(OnTagClickListener listener) {
        mListener = listener;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext,R.layout.activity_make_tag_item,null);
        TagViewHolder viewHolder=new TagViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TagViewHolder holder, final int position) {
        holder.tv_tag.setText(tagList[position]);
        holder.tv_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onClick(v,position);
                    Log.i("MoreChallengeAdapter", "onClick: "+position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.length;
    }

    class TagViewHolder extends RecyclerView.ViewHolder {

        TextView tv_tag;

        public TagViewHolder(View itemView) {
            super(itemView);
            tv_tag= (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }

    public interface OnTagClickListener{
        void onClick(View view, int position);
    }
}
