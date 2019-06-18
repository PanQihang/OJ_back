package com.oj.service.testing;

import com.oj.entity.testing.Case;

import java.util.List;
import java.util.Map;

public interface CaseService {

    //测试数据删除
    void CaseDelete(String id);

    //测试数据更新或保存
    void saveOrUpadateCase(Case[] c);

    //根据题目id获取测试数据
    List<Map> getCase(String id);

    //根据题目删除测试数据
    void CaseDeleteByProblem(String id);

}
