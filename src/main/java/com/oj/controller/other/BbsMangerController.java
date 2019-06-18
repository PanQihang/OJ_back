package com.oj.controller.other;

import com.oj.service.other.BbsMangerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/blocksMn")
public class BbsMangerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private BbsMangerService BbsMangerService;
    //返回主题管理页面
    @RequestMapping("/")
    public String index() {
        return "other/bbsManger";
    }

    @PostMapping("/getTopic")
    @ResponseBody
    public List<Map> getTopicMaplist() {
        List<Map> list = BbsMangerService.getTopicMaplist();

        return list;
    }
    //增加一个话题
    @PostMapping("/addTopic")
    @ResponseBody
    public boolean addTopic(@RequestBody Map<String, Object> param,HttpServletRequest request) {
        param.put("admin_id",request.getSession().getAttribute("user_id").toString());
        System.out.println(param);
       BbsMangerService.addTopic(param);

        return true;
    }
    //修改一个话题
    @PostMapping("/saveTopic")
    @ResponseBody
    public boolean saveTopic(@RequestBody Map<String, Object> param) {
        BbsMangerService.saveTopic(param);

        return true;
    }
    //删除话题
    @PostMapping("/delTopic")
    @ResponseBody
    public boolean delTopic(@RequestBody Map<String,  String> param) {

        BbsMangerService.delTopic(param);

        return true;
    }

    //返回文章列表
    @RequestMapping("/showarticelList/{id}")
    public String showarticelList(@PathVariable String id,Model model){
        Map<String, Object> info = new HashMap<>();
        List<Map> list=BbsMangerService.showarticelList(id);
        info.put("postlist",list);
        model.addAttribute("info",info);
        return "other/postManger";
    }

    //将文章置顶
    @PostMapping("/updateFlagpost")
    @ResponseBody
    public boolean updateFlagpost(@RequestBody Map<String,  String> param) {
         System.out.println(param);
        BbsMangerService.updateFlagpost(param);

        return true;
    }

    //返回文章内容
    @PostMapping("/showarticel")
    @ResponseBody
    public Map<String,Object> showarticel(@RequestBody Map<String, Object> param) {

        return BbsMangerService.showarticel(param);
    }


}
