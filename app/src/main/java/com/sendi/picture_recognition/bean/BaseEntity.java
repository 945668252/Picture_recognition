package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/4/26.
 * 实体基类
 */

public class BaseEntity<T> {

    private static int SUCCESS_CODE=520;
    private  int code;
    private T result;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "code=" + code +
                ", result=" + result +
                '}';
    }

    public boolean isSuccess(){
       return getCode()==SUCCESS_CODE;
   }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return result;
    }

    public void setData(T data) {
        this.result = data;
    }
}
