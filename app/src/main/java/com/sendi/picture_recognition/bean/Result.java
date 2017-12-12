package com.sendi.picture_recognition.bean;

/**
 * Created by Administrator on 2017/5/4.
 */

public class Result {
    private String result;

    public Result(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result='" + result + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    //    {result:1}//成功
}
