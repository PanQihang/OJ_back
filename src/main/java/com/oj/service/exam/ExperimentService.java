package com.oj.service.exam;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 实验管理Service接口
 */
public interface ExperimentService {

    //获取当前教师负责班级下的实验列表
    public List<Map> getExperInfo(String experName, String user_id);

    //获取题目列表
    public List<Map> loadPreSelectQuestion();

    //获取班级列表
    public List<Map> loadPreSelectClass(String user_id);

    //获取机房列表
    public List<Map> loadPreSelectJroom();

    //通过ID获取当前实验信息
    public Map getExperInfoById(String id);
    //通过ID获得当前所有的IP
    public List<Map> getIpInfoById(Map<String, String> param);
    //通过ID获取已选择试题列表
    public List<Map> getSelectedQueListById(String id);

    //通过ID获取已选择班级列表
    public List<Map> getSelectedClassListById(String id);

    //通过ID获取已选择机房列表
    public List<Map> getSelectedJroomListById(String id);

    //更新或新增实验
    public void saveOrUpdateExper(Map<String, Object> param, String user_id) throws Exception;

    //通过ID删除实验
    public void experDelete(String id);
}
