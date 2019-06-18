package com.oj.service.exam;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理Service接口
 */
public interface TestService {

    //获取当前教师负责班级下的考试列表
    public List<Map> getTestInfo(String testName, String user_id);

    //获取题目列表
    public List<Map> loadPreSelectQuestion();

    //获取班级列表
    public List<Map> loadPreSelectClass(String user_id);

    //获取机房列表
    public List<Map> loadPreSelectJroom();

    //通过ID获取当前考试信息
    public Map getTestInfoById(String id);
    //通过ID获得当前所有的IP
    public List<Map> getIpInfoById(Map<String, String> param);
    //通过ID获取已选择试题列表
    public List<Map> getSelectedQueListById(String id);

    //通过ID获取已选择班级列表
    public List<Map> getSelectedClassListById(String id);

    //通过ID获取已选择机房列表
    public List<Map> getSelectedJroomListById(String id);

    //更新或新增考试
    public void saveOrUpdateTest(Map<String, Object> param, String user_id) throws Exception;

    //通过ID删除考试
    public void testDelete(String id);

    //*********************************************************************************************************** xln
    /*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */
    //获取指定的考试成绩
    public List<Map> getTestScoreResultList(Map<String, String> param, String user_id);
    public List<Map> getcompScoreResultList(Map<String, String> param, String user_id);
    //获取指定考试的简要信息
    public Map getTestBriefInf(String id);
    //获取本次考试下的所有班级
    public List<Map> getTestClassList(String id);
    //获取考试题目集
    public List<Map> getTestProblemList(String id);
    //获取考试结果统计结果
    public List<Map> getTheStatisticalResult(Map<String, String> param);
    //删除指定考试里指定学生的ip绑定数据
    public boolean deleteTargetIpData(Map<String, String> param);

    //*********************************************** 定时任务-（实验/考试）结果统计
    //获取考试的提交的汇总情况
    public List<Map<String, Object>> getSubmitStateResult(String testId);
    //获取题目与分数
    public List<Map<String, Object>> getTestProblemInf(String testId);
    //获取正在进行的（实验/考试）
    public List<Map> getCurrentTestList();
    //获取正在进行的（实验），并整理当前已有的提交状态，汇总后覆盖原数据
    public void RunDoIt();
    //整理考试提交状态，并存储数据
    public boolean FunctionLY (String testId);



    //*********************************************** 定时任务-（实验/考试）相似度结果统计
    //处理考试提交代码，判断相似度，并保存到数据库
    public boolean FunctionPQH(String testId);
    //获取指定（实验/考试）相似判断结果
    public List<Map> getSimilarityResult(String testId);
    //获取指定的两个提交编号的代码
    public Map getTargetSubmitCode(Map t);

}
