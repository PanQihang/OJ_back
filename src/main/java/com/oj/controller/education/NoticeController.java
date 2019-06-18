package com.oj.controller.education;

import com.oj.entity.education.Notice;
import com.oj.service.education.NoticeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panqihang on 2019/4/2 14:33
 */

@Controller
@RequestMapping("/noticeMn")
public class NoticeController {
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoticeService noticeService;

    //返回notice管理界面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request) { return "education/notice"; }

    //通过参数返回对应IP列表
    @RequestMapping("/getNoticeMapList")
    @ResponseBody
    public List<Map> getIpMapList(@RequestBody Notice notice)
    {
        return noticeService.getNoticeMapList(notice);
    }

    //通过ID获取通知信息接口
    @PostMapping("/getNoticeById")
    @ResponseBody
    public Map getIpById(HttpServletRequest request){
        return noticeService.getNoticeById(request.getParameter("id").toString());
    }


    //IP更新或添加接口
    @PostMapping("/noticeSaveOrUpdate")
    @ResponseBody
    public Map<String, String> ipSaveOrUpdate(@RequestBody Notice notice){
        Map<String, String> map = new HashMap<>();
        try {
            noticeService.noticeSaveOrUpdate(notice);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    //IP删除接口
    @PostMapping("/noticeDelete")
    @ResponseBody
    public Map<String, String> ipDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            noticeService.noticeDelete(request.getParameter("id").toString());
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

}
