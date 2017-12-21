package com.sendi.picture_recognition.model.act;

import android.content.Context;
import android.util.Log;

import com.sendi.picture_recognition.bean.BaseEntity;
import com.sendi.picture_recognition.bean.RecordData;
import com.sendi.picture_recognition.model.abstract_act.AbsRecordModel;
import com.sendi.picture_recognition.service.RecordService;
import com.sendi.picture_recognition.utils.httputils.parseutils.SplitString;
import com.sendi.picture_recognition.utils.httputils.sqliteutils.SqliteDBUtils;
import com.sendi.userdb.RecordDataDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RecordModel extends AbsRecordModel {

    RecordService mService = this.createService(RecordService.class);

    @Override
    public Observable<BaseEntity<List<RecordData>>> getRecordData(String uId) {
        return mService.getRecordData(uId);
    }

    @Override
    public List<RecordData> getCacheData(Context context) {

        List<RecordData> recordList = new ArrayList<>();
        List<com.sendi.userdb.RecordData> cacheRecordDataList = SqliteDBUtils
                .getInstance(context)
                .getRecordDataDao()
                .queryBuilder()
                .listLazy();
        Log.i("TAG", "getCacheData: "+cacheRecordDataList);
        if (cacheRecordDataList==null)
            return recordList;
        for (com.sendi.userdb.RecordData record : cacheRecordDataList) {
            RecordData recordData = new RecordData();
            recordData.setStatus(record.getStatus());
            recordData.setImageUrl(record.getImg_url());
            recordData.setPid(record.getImg_id());
            String[] selectedTags = SplitString.StringToArray(record.getTags_selected());
            recordData.setSelectedTags(selectedTags);
            String[] unselectedTags = SplitString.StringToArray(record.getTags_unselected());
            recordData.setUnselectedTags(unselectedTags);
            recordList.add(recordData);
        }
        return recordList;

    }

    @Override
    public void cacheData(List<RecordData> recordDatas, Context context) {
        RecordDataDao recordDataDao = SqliteDBUtils
                .getInstance(context)
                .getRecordDataDao();

        for (RecordData record : recordDatas) {
            com.sendi.userdb.RecordData recordData = recordDataDao
                    .queryBuilder()
                    .where(RecordDataDao.Properties.Img_id.eq(record.getPid()))
                    .unique();
            if (recordData == null) {//说明不存在
                recordData = new com.sendi.userdb.RecordData();
                recordData.setImg_id(record.getPid());
                recordData.setImg_url(record.getImageUrl());
                recordData.setStatus(record.getStatus());
                String selectedTag = getTagsString(record.getSelectedTags());
                recordData.setTags_selected(selectedTag);//历史
                String unselectedTag = getTagsString(record.getUnselectedTags());
                recordData.setTags_unselected(unselectedTag);//推荐
                //插入数据
                recordDataDao.insert(recordData);
            }

        }
    }

    //将数组变成字符串，以便缓存
    private String getTagsString(String[] tagsArray) {
        String tag = "";
        for (int i = 0; i < tagsArray.length; i++) {
            if (i == 0) {
                tag = tagsArray[i];
            } else {
                tag = tag + "," + tagsArray[i];
            }
        }
        return tag;
    }
}
