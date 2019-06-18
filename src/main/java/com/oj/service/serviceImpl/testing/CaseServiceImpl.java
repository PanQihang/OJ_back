package com.oj.service.serviceImpl.testing;

import com.oj.entity.testing.Case;
import com.oj.mapper.testing.CaseMapper;
import com.oj.service.testing.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class CaseServiceImpl implements CaseService {

    //注入CaseMapper
    @Autowired(required = false)
    private CaseMapper mapper;

    //测试数据删除
    public void CaseDelete(String id){
        mapper.deleteCase(id);
    }

    //测试数据更新或保存
    public void saveOrUpadateCase(Case[] c){
        //如果问题不已经存在则添加
        for(int i = 0; i < c.length; i++){
            if(c[i].getId().equals("0")){
                mapper.addCase(c[i]);
            }
            else if(c[i].getId().equals("1")){
                mapper.addCaseWithoutProblemId(c[i]);
                System.out.println("新增题目");
            } //否则更新
            else{
                mapper.alterCase(c[i]);
            }
        }

    }

    //根据题目id获取测试数据
    public List<Map> getCase(String id){
        //System.out.println(id);
        return mapper.getProblemById(id);
    }

    @Override
    public void CaseDeleteByProblem(String id) {
        mapper.deleteCaseByProblem(id);
    }
}
