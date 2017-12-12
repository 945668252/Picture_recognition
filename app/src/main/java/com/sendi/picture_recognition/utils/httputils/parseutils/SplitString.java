package com.sendi.picture_recognition.utils.httputils.parseutils;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SplitString {
    public static String[] StringToArray(String string){
        String[] tagArray=string.split(",");
        return tagArray;
    }
}
