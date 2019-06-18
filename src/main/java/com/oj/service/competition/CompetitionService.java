package com.oj.service.competition;
import java.util.List;
import java.util.Map;

public interface CompetitionService {
    public List<Map> getcompetitionInfo(String experName);


    //获取题目列表
    public List<Map> loadPreSelectQuestion();

    //获取机房列表
    public List<Map> loadPreSelectJroom();
    //通过ID获取当前实验信息
    public Map getCompInfoById(String id);
    //通过ID获取已选择试题列表
    public List<Map> getSelectedQueListById(String id);
    //通过ID获取已选择机房列表
    public List<Map> getSelectedJroomListById(String id);
    //更新或新增实验
    public void saveOrUpdateComp(Map<String, Object> param, String user_id) throws Exception;
    //通过ID删除实验
    public void compDelete(String id);
}
