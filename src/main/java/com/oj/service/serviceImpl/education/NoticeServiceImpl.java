package com.oj.service.serviceImpl.education;

import com.oj.entity.education.Notice;
import com.oj.mapper.education.NoticeMapper;
import com.oj.service.education.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by panqihang on 2019/4/2 14:54
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private NoticeMapper noticeMapper;
    @Override
    public List<Map> getNoticeMapList(Notice notice) {
        return noticeMapper.getNoticeMaplist(notice);
    }

    @Override
    public Map getNoticeById(String id) {
        return noticeMapper.getNoticeById(id).get(0);
    }

    @Override
    public void noticeSaveOrUpdate(Notice notice) {
        if(StringUtils.isEmpty(notice.getId())){
            noticeMapper.save(notice);
        }else {
            noticeMapper.update(notice);
        }
    }

    @Override
    public void noticeDelete(String id) {
        noticeMapper.noticeDelete(id);
    }
}
