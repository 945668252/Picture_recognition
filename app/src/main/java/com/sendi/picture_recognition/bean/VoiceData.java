package com.sendi.picture_recognition.bean;

import java.util.List;

/**
 * 语音信息
 * Created by Administrator on 2017/5/25.
 */

public class VoiceData {
    /**
     * sn : 2
     * ls : true
     * bg : 0
     * ed : 0
     * ws : [{"bg":0,"cw":[{"sc":0,"w":"小花"}]},{"bg":0,"cw":[{"sc":0,"w":"猫"}]},{"bg":0,"cw":[{"sc":0,"w":"。"}]}]
     */

    private List<WsBean> ws;

    public List<WsBean> getWs() {
        return ws;
    }

    public void setWs(List<WsBean> ws) {
        this.ws = ws;
    }

    public static class WsBean {
        /**
         * bg : 0
         * cw : [{"sc":0,"w":"小花"}]
         */

        private List<CwBean> cw;

        @Override
        public String toString() {
            return "WsBean{" +
                    "cw=" + cw +
                    '}';
        }

        public List<CwBean> getCw() {
            return cw;
        }

        public void setCw(List<CwBean> cw) {
            this.cw = cw;
        }

        public static class CwBean {
            /**
             * sc : 0.0
             * w : 小花
             */

            private String w;

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }

            @Override
            public String toString() {
                return "CwBean{" +
                        "w='" + w + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "VoiceData{" +
                "ws=" + ws +
                '}';
    }
//    public ArrayList ws;
//
//    public class cwBean{
//        public ArrayList cw;
//    }
//
//    public class wBean{
//        public String w;
//    }
}
