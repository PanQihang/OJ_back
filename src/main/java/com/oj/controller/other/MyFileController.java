package com.oj.controller.other;

import com.oj.entity.other.MyFile;
import com.oj.service.other.MyFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author zt
 * @Time 2019年4月4日 15点00分
 * @Description 文件管理控制类
 */
@Controller
@RequestMapping("/myFile")
public class MyFileController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MyFileService myfileService;

    //返回文件管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request)
    {
        return "other/myFile";
    }

    @PostMapping("/getAdminSelectInfo")
    @ResponseBody
    //获取教师下拉信息
    public List<Map> getAdminSelectInfo(){
        List<Map> list = myfileService.getAdminSelectInfo();
        return list;
    }

    @PostMapping("/getFileMapList")
    @ResponseBody
    public List<Map> getFileMapList(HttpServletRequest request, @RequestBody Map<String, String> param)
    {
        String id = request.getSession().getAttribute("user_id").toString();
        param.put("id", id);
        return myfileService.getFileMapList(param);
    }

    @RequestMapping("/uploadMyFile")
    @ResponseBody
    public Map<String, String> uploadMyFile(HttpServletRequest request, @RequestParam("file") MultipartFile file)
    {
        Map<String, String> map = new HashMap<>();
        System.out.println("flag = " + request.getParameter("flag"));
        System.out.println("session id" + request.getSession().getAttribute("user_id").toString());
        try {
            myfileService.uploadMyFile(request, file);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("/checkFileName")
    @ResponseBody
    public Map<String, String> checkFileName(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try {
            String user_id = request.getSession().getAttribute("user_id").toString();
            myfileService.checkFileName(request.getParameter("name"), user_id);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            System.out.println("error my : "+ e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }


    @PostMapping("/fileDelete")
    @ResponseBody
    public Map<String, String> fileDelete(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try
        {
            myfileService.fileDelete(request);
            map.put("flag", "1");
            return map;
        }
        catch(Exception e)
        {
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @GetMapping("/downloadFile")
    @ResponseBody
    public void downloadFile(HttpServletRequest request, HttpServletResponse response)
    {
        String id = request.getParameter("id");
        //System.out.println("id : " + id);
        myfileService.downloadFile(id, response);
    }

    @PostMapping("/checkFileExistence")
    @ResponseBody
    public Map<String, String>checkFileExistence(HttpServletRequest request)
    {
        Map<String, String>map = new HashMap<>();
        if(myfileService.checkFileExistence(request.getParameter("id")))
        {
            map.put("flag", "1");
            return map;
        }
        else
        {
            map.put("flag", "0");
            return map;
        }
    }

    @PostMapping("/fileFlag")
    @ResponseBody
    public List<Map> fileFlag(HttpServletRequest request)
    {
        String id = request.getParameter("id");
        //System.out.println(id);
        //System.out.println(myfileService.fileFlag(id));
        return myfileService.fileFlag(id);
    }

    @PostMapping("/saveFileFlag")
    @ResponseBody
    public Map<String, String> saveFileFlag(String id, String flag)
    {
        Map<String, String> map = new HashMap<>();
        try {
            myfileService.saveFileFlag(id, flag);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
}
