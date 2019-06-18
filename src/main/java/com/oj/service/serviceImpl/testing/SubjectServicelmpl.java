package com.oj.service.serviceImpl.testing;
import com.oj.entity.testing.Subject;
import com.oj.mapper.testing.SubjectMapper;
import com.oj.service.testing.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author liyue
 * @Time 2019年3月27日 13点38分
 * @Description 主题功能相关功能Service接口
 */
@Service
public class SubjectServicelmpl implements SubjectService{

    @Autowired(required = false)
    private SubjectMapper mapper;

    /**
     * 获取主题信息列表接口功能实现
     * @return
     */

    @Override
    public List<Map> getSubjectMaplist() {
        List<Map> list = mapper.getSubjectMaplist();
        for (Map s : list) {
            if(!StringUtils.isEmpty(s.get("description"))){
                s.put("description", Integer.valueOf(s.get("description").toString()));
            }


        }
        return mapper.getSubjectMaplist();
    }
    /**
     * 保存主题信息接口功能实现
     * @param subject
     */
    @Transactional
    @Override
    public void subjectSave(Subject subject) {
        //若"auth_parent"字段为""则将其置为空防止错误
        if ("".equals(subject.getSubject_parent())){
            System.out.println(subject.getSubject_parent());
            subject.setSubject_parent(null);
        }
        //保存新权限
        mapper.subjectSave(subject);

    }
    /**
     * 主题更新接口功能实现
     * @param subject
     */
    @Override
    public void subjectUpdate(Subject subject) {
        mapper.subjectUpdate(subject);
    }
    /**
     * 通过id获取主题信息接口功能实现
     * @param id
     * @return
     */
    @Override
    public Map getSubjectById(String id) {
        return mapper.getSubjectById(id).get(0);
    }
    /**
     * 主题删除接口功能实现
     * @param id
     */
    @Transactional
    @Override
    public void subjectDelete(String id) {
        List<String> subjectIdList = new ArrayList<>();
        subjectIdList.add(id);
        //获取当前权限和子权限的Id列表
        subjectIdList.addAll(mapper.getChildSubjectIds(id));
        for (String subjectId : subjectIdList){
            //通过权限Id删除权限
            mapper.subjectDelete(subjectId);
        }
    }
}
