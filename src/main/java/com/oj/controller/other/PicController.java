package com.oj.controller.other;

import com.oj.service.other.PicService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zt
 * @Time 2019年5月16日 13点24分
 * @Description 图片轮播管理控制类
 */
@Controller
@RequestMapping("/pic")
public class PicController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PicService picService;

    //返回文件管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request)
    {
        return "other/pic";
    }

    @PostMapping("/getPicMapList")
    @ResponseBody
    public List<Map> getPicMapList(HttpServletRequest request, @RequestBody Map<String, String> param)
    {
        //String id = request.getSession().getAttribute("user_id").toString();
        //param.put("id", id);
        return picService.getPicMapList(param);
    }

    @RequestMapping("/uploadPic")
    @ResponseBody
    public Map<String, String> uploadPic(HttpServletRequest request, @RequestParam("file") MultipartFile file)
    {
        Map<String, String> map = new HashMap<>();
        System.out.println("session id" + request.getSession().getAttribute("user_id").toString());
        System.out.println("id: "+request.getParameter("id"));
        System.out.println("name: "+request.getParameter("name"));
        System.out.println("describes: "+request.getParameter("describes"));
        System.out.println("picDesc: " + request.getParameter("picDesc"));
        try {
            picService.uploadPic(request, file);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
    @PostMapping("/picEdit")
    @ResponseBody
    public List<Map> picEdit(HttpServletRequest request)
    {
        String id = request.getParameter("id");
        //System.out.println(id);
        //System.out.println(myfileService.fileFlag(id));
        return picService.picEdit(id);
    }


    @PostMapping("/saveEditPic")
    @ResponseBody
    public Map<String, String> saveEditPic(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try {
            picService.saveEditPic(param, request);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    //新增的话 保存基本的信息且把upload/img/tmp/sessionId/fileName中的图片拷贝到/upload/img/fileName中
    @PostMapping("/savePicMsg")
    @ResponseBody
    public Map<String, String> savePicMsg(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        //System.out.println(param.toString());
        try {
            picService.savePicMsg(param, request);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }


    //展示文章编辑页面
    @RequestMapping("/addPic/{id}")
    public String addPic(@PathVariable String id, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", id);
        model.addAttribute("info", info);
        return "other/addPic";
    }


    //展示文章轮播编辑页面
    @RequestMapping("/adminPic")
    public String adminPic(Model model){
        Map<String, Object> info = new HashMap<>();
        info.put("selectedPicList", picService.selectedPicList());
        System.out.println(picService.selectedPicList().toString());
        model.addAttribute("info", info);
        return "other/adminPic";
    }

    @PostMapping("/picDelete")
    @ResponseBody
    public Map<String, String> picDelete(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try
        {
            picService.picDelete(request);
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

    @PostMapping("/loadPic")
    @ResponseBody
    public Map<String, String> loadPic(HttpServletRequest request)
    {
        Map<String, String> map = picService.loadPic(request);
        System.out.println(map.toString());
        return map;
    }

    @PostMapping("/getSelectPic")
    @ResponseBody
    public List<Map> getSelectPic(HttpServletRequest request)
    {
        List<Map> map = picService.getSelectPic(request);
        System.out.println(map.toString());
        return map;
    }

    @PostMapping("/saveAdminPic")
    @ResponseBody
    public Map<String, String> saveAdminPic(@RequestBody Map<String, Object> param, HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try
        {
            picService.saveAdminPic(param, request);
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

    @PostMapping("/uploadPicToShow")
    @ResponseBody
    public Map<String, String> uploadPicToShow(HttpServletRequest request, @RequestParam("file") MultipartFile file)
    {
        Map<String, String> map = new HashMap<>();
        try {
            picService.uploadPicToShow(request, file);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
    @PostMapping("/getSessionId")
    @ResponseBody
    public String getSessionId(HttpServletRequest request)
    {
        return String.valueOf(request.getSession().getAttribute("user_id"));
    }
}
