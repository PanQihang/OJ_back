package com.oj.service.education;

import com.oj.entity.education.Course;
import com.oj.mapper.provider.system.UserProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface CourseService {
    public Map getCourseById(String id);
    public Map getCourseByName(String name);
    // 获取课程列表
    public List<Map> getCourseMapList(Map<String, String> param);
    //保存或更新课程
    public void saveOrUpdateCourse(Course course) throws Exception;
    //课程删除
    public void courseDelete(String id);
    //获取班级列表
    public List<Map> getClassMapByCourseList(String id);
    //保存课程绑定班级的信息
    public void saveCourseClassList(Map<String, Object> param);
}
