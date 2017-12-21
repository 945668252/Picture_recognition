package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sendi.picture_recognition.R;


import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

//    private final int NORMAL_TYPE=1;
//    private final int ADD_TYPE=2;
    private List<String> tagList;
    private Context mContext;
    private OnClickListener mListener;
    private boolean isAdd=false;
    public TagAdapter(List<String> tagList, Context context) {
        this.tagList = tagList;
        mContext = context;
    }

    public void setListener(OnClickListener listener) {
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
        //如果是最后一个，则将TextView隐藏，显示加号
        if (isAdd&&position==tagList.size()-1){
            holder.ib_add.setVisibility(View.VISIBLE);
            holder.tv_tag.setVisibility(View.GONE);
            holder.ib_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        mListener.onAddClick(position);
                    }
                }
            });
            return;
        }
        holder.tv_tag.setVisibility(View.VISIBLE);
        holder.ib_add.setVisibility(View.GONE);
        holder.tv_tag.setText(tagList.get(position));
        //设置点击监听
        holder.tv_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onClick(v,position);
                }
            }
        });
        //设置长按监听
        holder.tv_tag.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener!=null){
                    mListener.onLongClick(v,position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    class TagViewHolder extends RecyclerView.ViewHolder {

        ImageButton ib_add;
        TextView tv_tag;
        public TagViewHolder(View itemView) {
            super(itemView);
            tv_tag= (TextView) itemView.findViewById(R.id.tv_tag);
            ib_add= (ImageButton) itemView.findViewById(R.id.ib_add);
        }
    }

    /**
     * 添加加号
     */
    public void addView(boolean isAdd,List<String> selectedTagList){
        this.isAdd=isAdd;
        if (isAdd){
            tagList.add(" ");
        }
        notifyDataSetChanged();
    }
    public void addData(boolean isAdd,List<String> selectedTagList){
        this.isAdd=isAdd;
//        if (isAdd){
//            tagList.clear();
//            tagList=selectedTagList;
//        }
        tagList.add(" ");
        notifyDataSetChanged();
        Log.i("TAG", "addData: "+tagList);

    }

    public interface OnClickListener{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
        void onAddClick(int position);
    }
}
