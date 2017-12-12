package com.sendi.picture_recognition.controller;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageLoader;
import com.sendi.picture_recognition.R;

/**
 * Created by Administrator on 2017/4/28.
 */

public class GlideLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imageView);
    }
}
