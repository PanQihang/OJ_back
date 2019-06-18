package com.oj.service.testing;

import com.oj.entity.testing.Problem;
import java.util.List;
import java.util.Map;

/**
 * @author zhouli
 * Time 2019年4月1日 11点03分
 * Description 题目管理相关功能Service接口
 */
public interface ProblemsService {

    //返回Map类型的List
    List<Map> getProblemsMapList(Map<String, String> param);

    //问题删除
     void problemDelete(String id);

    //问题更新或保存
    void saveOrUpadateProblem(Problem problem) throws Exception;

    //获取单个问题的详细信息
     List<Map> getProblemDetails(String id);

    //对问题的提交状况进行分析
     List<Map> analyzeProblem(String id);

}
