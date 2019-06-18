package com.oj.service.serviceImpl.education;

import com.oj.entity.education.Class;
import com.oj.mapper.education.ClassMapper;
import com.oj.service.education.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zt
 * @Time 2019年4月2日 17点52分
 * @Description 班级管理相关功能Service接口功能实现类
 */

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired(required = false)
    private ClassMapper mapper;

    public List<Map> getClassMapList(Map<String, String> param)
    {
        return mapper.getClassMapList(param);
    }

    //获取学院下拉信息
    public List<Map> getMajorSelectInfo()
    {
        return mapper.getMajorSelectInfo();
    }

    //获取年级下拉信息
    public List<Map> getGradeSelectInfo()
    {
        return mapper.getGradeSelectInfo();
    }
    //保存或更新班级
    public void saveOrUpdateClass(Class clas) throws Exception
    {
        //若当前班级号已经存在，则抛出班级已存在的异常
        if(mapper.getClassByName(clas.getName()).size()>0){
            throw new Exception("当前班级名称已存在!");
        }else{
            //若班级id为空，为保存
            if (StringUtils.isEmpty(clas.getId())){
                    mapper.save(clas);
            }else{
                mapper.update(clas);
            }
        }
    }

    @Transactional
    //班级删除
    public void classDelete(String id)
    {
        mapper.classDelete(id);
    }

    //@Override
    //获取学生列表
    public List<Map> getStudentMapByClassList(String id) {
        return mapper.getStudentMapByClassList(id);
    }
}
