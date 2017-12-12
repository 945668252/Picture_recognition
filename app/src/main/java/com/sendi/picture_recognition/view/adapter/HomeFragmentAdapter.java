package com.sendi.picture_recognition.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.bean.ImgInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.HomeViewHolder> {
    private Context mContext;
    private List<ImgInfo> imgInfoLists;

    public HomeFragmentAdapter(Context context, List<ImgInfo> imgLists) {
        this.mContext = context;
        this.imgInfoLists = imgLists;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.home_fragment_item, null);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        //加载图片
        Glide.with(mContext)
                .load(imgInfoLists.get(position).getUrl())
                .error(R.mipmap.app_logo)
                .centerCrop()
                .into(holder.avatarImageView);
    }

    @Override
    public int getItemCount() {
        return imgInfoLists.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImageView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
