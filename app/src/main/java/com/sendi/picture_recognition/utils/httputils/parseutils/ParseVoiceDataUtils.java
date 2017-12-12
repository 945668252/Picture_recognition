package com.sendi.picture_recognition.utils.httputils.parseutils;

import com.google.gson.Gson;
import com.sendi.picture_recognition.bean.VoiceData;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ParseVoiceDataUtils {

    public static String getWord(String result){
        Gson gson=new Gson();
        VoiceData vData=gson.fromJson(result, VoiceData.class);
        StringBuffer sb=new StringBuffer();
        for (VoiceData.WsBean ws : vData.getWs()) {
            for (VoiceData.WsBean.CwBean cw: ws.getCw()) {
                sb.append(cw.getW());
            }
        }

        return sb.toString();
    }
}
