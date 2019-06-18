package com.oj.controller.education;

import com.oj.entity.education.Course;
import com.oj.entity.system.User;
import com.oj.frameUtil.LogUtil;
import com.oj.service.system.AuthService;
import com.oj.service.system.LoginService;
import com.oj.service.system.UserService;
import com.oj.service.education.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zt
 * @Time 2019年4月1日 12点35分
 * @Description 课程管理控制类
 */
@Controller
@RequestMapping("/courseMn")
public class CourseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseService courseService;

    //返回课程管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request)
    {
        return "education/course";
    }
    //通过课程ID查找课程信息接口
    @PostMapping("/getCourseById")
    @ResponseBody
    public Map getCourseById(HttpServletRequest request){
        return courseService.getCourseById(request.getParameter("id").toString());
    }
    //通过课程名查找课程信息接口
    @PostMapping("/getCourseByName")
    @ResponseBody
    public Map getCourseByName(HttpServletRequest request){
        return courseService.getCourseByName(request.getParameter("name").toString());
    }

    //查找课程信息
    @PostMapping("/getCourseMapList")
    @ResponseBody
    public List<Map> getCourseMapList(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        List<Map> list = courseService.getCourseMapList(param);
        return list;
    }

    //保存或更新课程信息
    @PostMapping("/saveOrUpdateCourse")
    @ResponseBody
    public Map<String, String> saveOrUpdateCourse(@RequestBody Course course)
    {
        Map<String, String> map = new HashMap<>();
        try {
            courseService.saveOrUpdateCourse(course);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
    //课程信息的删除
    @PostMapping("/courseDelete")
    @ResponseBody
    public Map<String, String> courseDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            courseService.courseDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    //根据课程号查找对应班级信息
    @PostMapping("/getClassMapByCourseList")
    @ResponseBody
    public List<Map> getClassMapByCourseList(HttpServletRequest request)
    {
        return courseService.getClassMapByCourseList(request.getParameter("id"));
    }

    //课程绑定班级的保存
    @PostMapping("/saveCourseClassList")
    @ResponseBody
    public Map<String, String> saveCourseClassList(@RequestBody Map<String, Object> param){
        Map<String, String> map = new HashMap<>();
        try {
            courseService.saveCourseClassList(param);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

}
