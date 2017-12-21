package com.sendi.picture_recognition.utils.httputils;

import com.sendi.picture_recognition.config.GlobalConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RetrofitFactory {
//    public static RetrofitFactory mRetrofitFactory;
//    private static  ApiServer mApiServer;
    private static Retrofit mRetrofit;

//    private RetrofitFactory(){
//        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
//                .connectTimeout(GlobalConfig.HTTP_TIME, TimeUnit.SECONDS)
//                .readTimeout(GlobalConfig.HTTP_TIME,TimeUnit.SECONDS)
//                .addInterceptor(InterceptorUtil.HeaderInterceptor())
//                .addInterceptor(InterceptorUtil.LogInterceptor())
//                .build();
//        Retrofit mRetrofit=new Retrofit.Builder()
//                .baseUrl(GlobalConfig.CONTENT_SERVER)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(mOkHttpClient)
//                .build();
//        mApiServer=mRetrofit.create(ApiServer.class);
//    }
//
//    public static RetrofitFactory getInstance(){
//        if (mRetrofitFactory==null){
//            synchronized (RetrofitFactory.class){
//                if (mRetrofitFactory==null){
//                    mRetrofitFactory=new RetrofitFactory();
//                }
//            }
//        }
//        return mRetrofitFactory;
//    }
//    public ApiServer getAPI(){
//        return mApiServer;
//    }

    public static <T> T getService(Class<T> tClass){
        if(mRetrofit==null){
            synchronized (RetrofitFactory.class){
                if (mRetrofit==null){

                    OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                            .connectTimeout(GlobalConfig.HTTP_TIME, TimeUnit.SECONDS)
                            .readTimeout(GlobalConfig.HTTP_TIME,TimeUnit.SECONDS)
                            .addInterceptor(InterceptorUtil.HeaderInterceptor())
                            .addInterceptor(InterceptorUtil.LogInterceptor())
                            .build();

                    mRetrofit =new Retrofit.Builder()
                            .baseUrl(GlobalConfig.CONTENT_SERVER)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .build();
                }
            }
        }
        return mRetrofit.create(tClass);
    }


}
