package com.oj.service.serviceImpl.exam;

import com.oj.mapper.exam.SummaryMapper;
import com.oj.service.exam.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class SummaryServiceImpl implements SummaryService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //注入UserMapper
    @Autowired(required = false)
    private SummaryMapper mapper;

    @Override
    public List<Map> getSummary(String id,String role) {
        //if(role.equals("32"))
        //{
            return mapper.getAllSummary();
        //}
        //else
        //{
            //return mapper.getSummary(id);
        //}
    }

    @Override
    public void deleteSummary(String id) {
        mapper.deleteSummary(id);
        mapper.deleteReporttest(id);
    }

    @Override
    public List<Map> getTest(String id) {
        return mapper.getTest(id);
    }

    @Override
    public void addSummary(String id, String name, List list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        mapper.addSummary(name,time,id);
        String report_id = mapper.selectId();
        for(int i=0; i<list.size();i++)
        {
            mapper.addReport_test(report_id,list.get(i).toString());
        }
    }

    @Override
    public List<Map> getTestList(String id) {
        return mapper.getTestList(id);
    }

    @Override
    //获取指定的考试成绩
    public List<Map> getTestScoreResultList(Map<String, String> param, String user_id){
        String id = param.get("testId");
        List<Map> map = mapper.getTestScoreResult(id);
        for(int i=0; i<map.size(); i++)
        {
            if(map.get(i).get("result") == null)
            {
                map.get(i).put("result",0);
            }
            if(map.get(i).get("all") == null)
            {
                map.get(i).put("all",0);
            }
        }
        return map;
    }

    @Override
    public List<Map> getTheStatisticalResult(Map<String, String> param){
        String id = param.get("testId");
        return  mapper.getTheStatisticalResult(id);
    }

    @Override
    public String getName(String id) {
        return mapper.getSummaryName(id);
    }
}
