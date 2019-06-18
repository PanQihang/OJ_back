package com.oj.schedule;

import com.oj.mapper.system.HomepageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by AC on 2019/4/17 14:46
 */
@Component
public class demo {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        long lt = new Long(s)*1000;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    public static long dateToStamp(String s) throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long res = date.getTime()/1000;
        return res;
    }
    //注入UserMapper
    @Autowired(required = false)
    private HomepageMapper homepagemapper;

    @Scheduled(cron = "0 0 6 * * *")
    public void firstScheduledTasks(){
        Calendar cal=Calendar.getInstance();
        int y=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH)+1;
        int d=cal.get(Calendar.DATE);
        String datetime = y+"-"+m+"-"+d;
        try
        {
            long Stime = dateToStamp(datetime);
            homepagemapper.clearmonth();
            for(int i=0;i<30;i++)
            {
                long start = Stime-i*86400;
                long end = start + 86400;
                String starttime=String.valueOf(start),endtime=String.valueOf(end);
                String res = homepagemapper.getsubmit(starttime,endtime);
                String resac = homepagemapper.getac(starttime,endtime);
                starttime = stampToDate(starttime);
                homepagemapper.savemonth(starttime,resac,res);
            }
            log.info("提交统计定时成功");
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("提交统计定时失败"+e.getMessage());
        }
    }
}
