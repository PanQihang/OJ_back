package com.oj.service.serviceImpl.testing;

import com.oj.entity.testing.Problem;
import com.oj.service.testing.ProblemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oj.mapper.testing.ProblemsMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhouli
 * Time 2019年4月2日 11点58分
 * Description 题目管理相关功能接口实现类
 */

@Service
public class ProblemsServiceImpl implements ProblemsService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //注入ProblemMapper
    @Autowired(required = false)
    private ProblemsMapper mapper;

    @Override
    public List<Map> getProblemsMapList(Map<String, String> param){
        return mapper.getProblemsMapList(param);

    }

    /**
     * 用户删除接口功能实现
     * param id
     */
    @Override
    public void problemDelete(String id) {
        mapper.problemDelete(id);
    }

    //问题更新或保存
    public void saveOrUpadateProblem(Problem problem) throws Exception{
        //如果问题不已经存在则添加
        //System.out.println(problem.getId());
        if(problem.getId().equals("0")){
            mapper.insert(problem);
        }
        //否则更新
        else{
            System.out.println("456");
            mapper.update(problem);
        }
    }

    //获取单个问题的详细信息
    public List<Map> getProblemDetails(String id){
        return mapper.getProblemById(id);
    }

    //对问题的提交状况进行分析
    public List<Map> analyzeProblem(String id){
        return mapper.getProblemAnalyze(id);
    }



}


