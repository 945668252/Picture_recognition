package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class UserDataClass {

    public static void main(String[] args){
        Schema schema=new Schema(1,"com.sendi.userdb");

        //create the user table
        Entity user=schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("user_nickname");
        user.addStringProperty("user_id");
        user.addStringProperty("user_pic_url");
        user.addStringProperty("phone_number");
        user.addStringProperty("gander");
        user.addStringProperty("hobbies");
        //craete the record data table
        Entity recordData=schema.addEntity("RecordData");
        recordData.addStringProperty("img_url");//图片的URL
        recordData.addStringProperty("img_id");//图片的id
        recordData.addStringProperty("tags_selected");//历史标签
        recordData.addStringProperty("tags_unselected");//推荐标签
        recordData.addStringProperty("status");//status


        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
