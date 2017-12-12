package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class UserDataClass {
    public static void main(String[] args){
        Schema schema=new Schema(1,"com.sendi.userdb");

        //创建用户表new
        Entity user=schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("user_nickname");
        user.addStringProperty("user_id");
        user.addStringProperty("user_pic_url");
        user.addStringProperty("phone_number");
        user.addStringProperty("gander");
        user.addStringProperty("hobbies");
        //创建历史记录表
        Entity recordData=schema.addEntity("RecordData");
        recordData.addStringProperty("img_url");//图片的URL
        recordData.addStringProperty("img_id");//图片的id
        recordData.addStringProperty("tags_selected");//历史标签
        recordData.addStringProperty("tags_unselected");//推荐标签
        recordData.addStringProperty("status");//状态
        //创建分类图片表
        //创建热门图片表
        //创建最新图片表

        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
