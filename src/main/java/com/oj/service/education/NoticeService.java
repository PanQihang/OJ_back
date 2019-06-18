package com.oj.service.education;

import com.oj.entity.education.Notice;

import java.util.List;
import java.util.Map;

/**
 * Created by AC on 2019/4/2 14:50
 */
public interface NoticeService {
    //返回Map类型的List
    public List<Map> getNoticeMapList(Notice notice);

    //通过Id获取通知信息
    public Map getNoticeById(String id);

    //通知保存或更新
    public void noticeSaveOrUpdate(Notice notice);

    //通知删除
    public void noticeDelete(String id);
}
