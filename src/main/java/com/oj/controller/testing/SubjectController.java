package com.oj.controller.testing;

import com.oj.entity.testing.Subject;
import com.oj.service.testing.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/subjectsMn")
public class SubjectController{
    private Logger log = LoggerFactory.getLogger(this.getClass());
//    依赖注入UserSubjectService
    @Autowired
    private SubjectService subjectService;

    //返回主题管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        return "testing/subject";
    }

    //通过调用接口传递的条件，返回对应的主题信息JsonList
   @PostMapping("/getSubjectMaplist")
    @ResponseBody
   public List<Map> getSubjectMaplist() {
        List<Map> list = subjectService.getSubjectMaplist();
        return list;
    }
    //主题信息保存接口
    @PostMapping("/subjectSave")
    @ResponseBody
    public Map<String, String> subjectSave(@RequestBody Subject subject) {
        Map<String, String> map = new HashMap<>();
        try {
            subjectService.subjectSave(subject);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }
    //主题信息更新接口
    @PostMapping("/subjectUpdate")
    @ResponseBody
    public Map<String, String> subjectUpdate(@RequestBody Subject subject) {
        Map<String, String> map = new HashMap<>();
        try {
            System.out.println(subject);
            subjectService.subjectUpdate(subject);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }
    //通过主题ID查找主题信息接口
    @PostMapping("/getSubjectById")
    @ResponseBody
    public Map getSubjectById(HttpServletRequest request){
        return subjectService.getSubjectById(request.getParameter("id").toString());
    }
    //主题删除接口
    @PostMapping("/subjectDelete")
    @ResponseBody
    public Map<String, String> subjectDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            System.out.println(request.getParameter("id"));
            subjectService.subjectDelete(request.getParameter("id").toString());
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

}
