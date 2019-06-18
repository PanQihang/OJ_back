package com.oj.controller.education;


import com.oj.entity.education.Student;
import com.oj.entity.other.BulkAddStudentPackage;
import com.oj.service.education.StudentService;
import com.oj.service.other.ImportService;
import com.oj.service.serviceImpl.other.ImportServicelmpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 学生账号功能接口
 */

@Controller
@RequestMapping("/studentMn")
public class StudentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService service;




    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        return "education/studentMn";
    }

    //获取指定条件的学生集
    @PostMapping("/getTargetStudentList")
    @ResponseBody
    public List<Map> getTargetStudentList(@RequestBody Map<String, String> param, HttpServletRequest request){
        List<Map> list = service.getTargetStudentList(param);
        return list;
    }
    //获取所有的班级名称和对应id
    @PostMapping("/getClassList")
    @ResponseBody
    public List<Map> getClassList(){
        return service.getClassList();
    }
    //添加新的学生账号
    @PostMapping("/addNewStudent")
    @ResponseBody
    public Map<String, String> addNewStudent(@RequestBody Map<String, String> param, HttpServletRequest request){
        //why ？这里通过request的getParameter调用无法获取到json数据
        ImportServicelmpl Iservice = new ImportServicelmpl();
        Map<String, String> map = new HashMap<>();
        map.put("elementId", param.get("elementId"));
        try {
            Student student;
            //ImportServicelmpl的checkB调用有对数据格式的规范检查
            student = Iservice.checkB(param.get("account"), param.get("name"), param.get("class_id"));
            //System.out.println(student);
            service.addNewStudent(student);
            map.put("result", "succeed");
            return map;
        }catch (Exception e){
            map.put("result", "failed");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    //批量添加学生
    @PostMapping("/bulkAddNewStudent")
    @ResponseBody
    public Map<String, String> bulkAddNewStudent(@RequestBody BulkAddStudentPackage param, HttpServletRequest request){
        ImportServicelmpl Iservice = new ImportServicelmpl();
        Map<String, String> map = new HashMap<>();
        try {
            service.addMoreNewStudent(param);
            //System.out.println(param.getClassId()+", "+param.getData());
            map.put("result", "succeed");
            return map;
        }catch (Exception e){
            map.put("result", "failed");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    //通过指定学生编号删除对应学生账号
    @PostMapping("/deleteTheStudent")
    @ResponseBody
    public Map<String, String> deleteTheStudent(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            service.deleteTheStudentById(param.get("id"));
            map.put("result", "succeed");
            return map;
        }catch (Exception e){
            map.put("result", "failed");
            log.error(e.getMessage());
            return map;
        }
    }
    //通过指定学生编号修改学生信息(修改的属性不包括密码)
    @PostMapping("/changeTheStudent")
    @ResponseBody
    public Map<String, String> changeTheStudent(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            Student student = new Student(param.get("account"), " ", param.get("name"), param.get("class_id") );
            System.out.println(student);
            service.updateTheStudentById(param.get("id"), student);
            map.put("result", "succeed");
            return map;
        }catch (Exception e){
            map.put("result", "failed");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
    //通过指定学生编号修改其密码
    @PostMapping("/changeTheStudentPW")
    @ResponseBody
    public Map<String, String> changeTheStudentPW(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            service.updateTheStudentPWById(param.get("id"), param.get("password"));
            map.put("result", "succeed");
            return map;
        }catch (Exception e){
            map.put("result", "failed");
            log.error(e.getMessage());
            return map;
        }
    }
    //excel文件上传接口(暂废)
    @PostMapping("/upFile")
    @ResponseBody
    public Map<String, String> upFile(HttpServletRequest request, @RequestParam("file") MultipartFile file)  {
        Map<String, String> map = new HashMap<>();
        if(true){ //接口停用，后面的步骤不予处理
            map.put("result","failed");
            return map;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        ImportService importService= new ImportServicelmpl();
        // 测试MultipartFile接口的各个方法
        System.out.println("文件类型ContentType=" + file.getContentType());
        System.out.println("文件组件名称Name=" + file.getName());
        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
        System.out.println("文件大小Size=" + file.getSize()/1024 + "KB");


       // MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            map.put("result", "failed");
            map.put("message", "文件不能为空");
            return map;
        }
        try {
            InputStream inputStream = file.getInputStream();
            if(null==inputStream)
                System.out.println("#inputStream is null");
            //List<List<Object>> list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
            inputStream.close();
        }catch (Exception e){
            map.put("reuslt", "failed");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }


        map.put("result", "succeed");
        return map;
    }








}
