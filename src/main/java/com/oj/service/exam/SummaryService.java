package com.oj.service.exam;

import java.util.List;
import java.util.Map;

public interface SummaryService {
    //返回所有报表
    public List<Map> getSummary(String id, String role);

    //通过id删除报表
    public void deleteSummary(String id);

    //获取考试列表
    public List<Map> getTest(String id);

    //新增报表
    public void addSummary(String id, String name, List list);

    //获取考试题目集
    public List<Map> getTestList(String id);

    //获取指定的考试成绩
    public List<Map> getTestScoreResultList(Map<String, String> param, String user_id);

    //获取考试结果统计结果
    public List<Map> getTheStatisticalResult(Map<String, String> param);

    //获取报表名称
    public String getName(String id);
}
