package com.sendi.picture_recognition.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sendi.picture_recognition.R;
import com.sendi.picture_recognition.config.GlobalConfig;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.userdb.User;


import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class UserInfoAdapter extends BaseAdapter {
    private Context mContext;
    private User mUser;

    public UserInfoAdapter(Context context, User user) {
        mContext = context;
        mUser = user;
    }


    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        switch (position) {
            case 0:
                return mUser.getUser_pic_url();

            case 1:
                return mUser.getUser_nickname();
            case 2:
                return mUser.getUser_id();

            case 3:
                return mUser.getPhone_number();

            case 4:
                return mUser.getGander();

            case 5:
                return mUser.getHobbies();

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.activity_user_info_item, null);
            holder.content = (TextView) convertView.findViewById(R.id.tv_content_item);
            holder.item= (TextView) convertView.findViewById(R.id.tv_item);
            holder.pic = (ImageView) convertView.findViewById(R.id.iv_pic_item);
            holder.more = (ImageView) convertView.findViewById(R.id.iv_more_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (position) {
            case 0:
                holder.pic.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(GlobalConfig.USERPID)
                        .into(holder.pic);
                holder.content.setVisibility(View.INVISIBLE);
                holder.item.setText("头像");
                //设置头像
//                holder.pic.setImageResource(mUser.getUser_pic_id());
                break;
            case 1:
                holder.pic.setVisibility(View.INVISIBLE);
                holder.item.setText("名字");
                holder.content.setText(mUser.getUser_nickname());
                break;
            case 2:
                holder.pic.setVisibility(View.INVISIBLE);
                holder.item.setText("志愿号");
                holder.content.setText(mUser.getUser_id());
                break;
            case 3:
                holder.content.setText(mUser.getPhone_number());
                holder.item.setText("手机号码");
                holder.pic.setVisibility(View.INVISIBLE);
                break;
            case 4:
                if (mUser.getGander().equals("1")){

                    holder.content.setText("男");
                }else {
                    holder.content.setText("女");
                }
                holder.item.setText("性别");
                holder.pic.setVisibility(View.INVISIBLE);
                break;
            case 5:
                holder.content.setText(mUser.getHobbies());
                holder.item.setText("喜欢的事物");
                holder.pic.setVisibility(View.INVISIBLE);
                break;
        }
        return convertView;
    }

    class ViewHolder {
        public TextView item;
        public TextView content;
        public ImageView pic;
        public ImageView more;

    }
}
