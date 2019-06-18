package com.oj.service.serviceImpl.education;

import com.oj.entity.education.Course;
import com.oj.frameUtil.OJPWD;
import com.oj.mapper.education.CourseMapper;
import com.oj.mapper.system.AuthMapper;
import com.oj.service.education.CourseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zt
 * @Time 2019年4月1日 12点48分
 * @Description 课程管理相关功能Service接口功能实现类
 */

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired(required = false)
    private CourseMapper mapper;
    /**
     * 通过id获取课程信息接口功能实现
     * @param id
     * @return
     */
    //@Override
    public Map getCourseById(String id) {
        return mapper.getCourseById(id).get(0);
    }
    public Map getCourseByName(String name)
    {
        return mapper.getCourseByName(name).get(0);
    }

    public List<Map> getCourseMapList(Map<String, String> param)
    {
        return mapper.getCourseMapList(param);
    }
    public void saveOrUpdateCourse(Course course) throws Exception
    {
        if(mapper.getCourseByName(course.getName()).size()>0){
            throw new Exception("当前课程名称已存在!");
        }else{
            //若课程id为空，为保存
            if (StringUtils.isEmpty(course.getId())){
                //若当前课程号已经存在，则抛出课程已存在的异常
                    mapper.save(course);
            }else{
                mapper.update(course);
            }
        }
    }

    @Transactional
    //@Override
    public void courseDelete(String id) {
        //在删除课程之前应该先对其关联的班级信息进行解绑
        mapper.courseClassDelete(id);
        //解绑成功之后，再进行课程删除操作
        mapper.courseDelete(id);
    }

    //@Override
    public List<Map> getClassMapByCourseList(String id) {
        return mapper.getClassMapByCourseList(id);
    }

    //@Transactional
    //@Override
    public void saveCourseClassList(Map<String, Object> param)
    {
        String course_id = param.get("course_id").toString();
        List<String> class_list = (List<String>)param.get("class_list");
        //先解除课程的全部班级绑定
        mapper.courseClassDelete(course_id);
        //重新对用户的课程进行绑定
        for (String class_id : class_list){
            mapper.saveCourseClassList(course_id, class_id);
        }
    }
}
