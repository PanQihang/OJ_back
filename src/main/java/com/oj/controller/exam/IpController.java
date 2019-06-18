package com.oj.controller.exam;

import com.oj.entity.exam.Ip;
import com.oj.service.exam.IpService;
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
import org.slf4j.Logger;

/**
 * Created by panqihang on 2019/3/21 12:22
 */
@Controller
@RequestMapping("/ipMn")
public class IpController {
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    //依赖注入IpService
    @Autowired
    private IpService ipService;

    //返回ip管理界面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request) { return "exam/ip"; }

    //通过参数返回对应IP列表
    @RequestMapping("/getIpMapList")
    @ResponseBody
    public List<Map> getIpMapList(@RequestBody Ip ip)
    {
        return ipService.getIpMapList(ip);
    }

    //通过ID获取IP信息接口
    @PostMapping("/getIpById")
    @ResponseBody
    public Map getIpById(HttpServletRequest request){
        return ipService.getIpById(request.getParameter("id").toString());
    }

    //IP更新或添加接口
    @PostMapping("/ipSaveOrUpdate")
    @ResponseBody
    public Map<String, String> ipSaveOrUpdate(@RequestBody Ip ip){
        Map<String, String> map = new HashMap<>();
        try {
            ipService.ipSaveOrUpdate(ip);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    //IP删除接口
    @PostMapping("/ipDelete")
    @ResponseBody
    public Map<String, String> ipDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            ipService.ipDelete(request.getParameter("id").toString());
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }
}
