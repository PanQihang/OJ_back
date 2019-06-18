package com.oj.service.serviceImpl.system;

import com.oj.mapper.system.HomepageMapper;
import com.oj.service.system.HomepageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by panqihang on 2019/4/12 17:00
 */
@Service
public class HomepageServiceImpl implements HomepageService
{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //注入UserMapper
    @Autowired(required = false)
    private HomepageMapper homepagemapper;

    @Override
    public List<Map> getPending(String id)
    {
        List<Map> l = homepagemapper.getpending(id);
        int len = l.size();
        long nowtime = Calendar.getInstance().getTimeInMillis();
        for(int i=0;i<len;i++)
        {
            long starttime = (int)l.get(i).get("start_time");
            long endtime = (int)l.get(i).get("end_time");
            starttime*=1000;
            endtime*=1000;
            if(starttime<nowtime&&nowtime<endtime)
            {
                l.get(i).put("state","正在进行");
            }
            else if(nowtime<starttime)
            {
                l.get(i).put("state","未开始");
            }
            else if(nowtime>endtime)
            {
                l.get(i).put("state","已结束");
            }
        }
        return l;
    }
    @Override
    public List<Map> getSubmit() {
        List<Map> list = new  ArrayList();
        list = homepagemapper.getmonthsubmit();
        return list;
    }
}
