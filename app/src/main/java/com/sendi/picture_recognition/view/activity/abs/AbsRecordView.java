package com.sendi.picture_recognition.view.activity.abs;

import com.sendi.picture_recognition.base.BaseActivity;
import com.sendi.picture_recognition.bean.RecordData;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public abstract class AbsRecordView extends BaseActivity {
    /**
     * 展示数据
     * @param recordData
     */
    public abstract void showRecord(List<RecordData> recordData);
}
