package com.sendi.picture_recognition.utils.httputils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 拦截器工具类
 * Created by Administrator on 2017/4/26.
 */

public class InterceptorUtil {
    public static String TAG="ASENDI";

    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Log.i(TAG, "log: "+message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    public static Interceptor HeaderInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest=chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等,PS我会在下一篇文章总写拦截token方法
                return chain.proceed(mRequest);
            }
        };
    }
}
