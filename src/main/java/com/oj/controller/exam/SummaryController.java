package com.oj.controller.exam;
import com.oj.service.exam.SummaryService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/summaryMn")
public class SummaryController {
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    //依赖注入IpService
    @Autowired
    private SummaryService service;
    //返回成绩报表管理界面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request) { return "exam/summary"; }

    //返回成绩报表列表
    @RequestMapping("/getSummary")
    @ResponseBody
    public List<Map> getSummary(ModelMap modelMap, HttpServletRequest request)
    {
        String role = (String) request.getSession().getAttribute("user_role");
        String user_id = request.getSession().getAttribute("user_id").toString();
        List<Map> map = service.getSummary(user_id,role);
        return map;
    }

    //删除成绩报表列表
    @RequestMapping("/deleteSummary")
    @ResponseBody
    public int deleteSummary(ModelMap modelMap, HttpServletRequest request)
    {
        String id = request.getParameter("id").toString();
        try
        {
            service.deleteSummary(id);
            return 1;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return 0;
        }
    }

    //返回新增报表页面
    @RequestMapping("/addSummaryHTML")
    public String addSummaryHTML(ModelMap modelMap, HttpServletRequest request)
    {
        return "exam/addSummary";
    }

    //获取考试列表
    @RequestMapping("/getTest")
    @ResponseBody
    public List<Map> getTest(ModelMap modelMap, HttpServletRequest request)
    {
        String user_id = request.getSession().getAttribute("user_id").toString();
        return service.getTest(user_id);
    }

    //新增报表
    @RequestMapping("/addSummary")
    @ResponseBody
    public int addSummary(@RequestBody Map<String, Object> param, HttpServletRequest request)
    {
        String user_id = request.getSession().getAttribute("user_id").toString();
        String name = param.get("name").toString();
        List list = (List) param.get("list");
        try {
            service.addSummary(user_id, name, list);
            return 1;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return 0;
        }
    }

    //返回查看界面
    @RequestMapping("/showSummary/{tid}")
    public String showSummary(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        String name = service.getName(tid);
        info.put("tid", tid);
        info.put("name",name);
        model.addAttribute("info", info);
        return "exam/showSummary";
    }

    //获取考试列表
    @RequestMapping("/getTestList")
    @ResponseBody
    public List<Map> getTestProblemList(@RequestBody Map<String, String> param, HttpServletRequest request){
        return service.getTestList(param.get("testId"));
    }

    //获取考试结果统计结果
    @RequestMapping("/getTheStatisticalResult")
    @ResponseBody
    public Map getTheStatisticalResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map result = new HashMap<String, Object>();
        List<Map> list = service.getTheStatisticalResult(param);
        List keyList = new LinkedList<String>();
        for(Map k : list){
            k.put("name", k.get("name")+"分");
            keyList.add(k.get("name"));
        }
        result.put("key", keyList);
        result.put("data", list);
       return result;
    }

    //获取考试成绩集
    @PostMapping("/getTestScoreResultList")
    @ResponseBody
    public List<Map> getTestScoreResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        return service.getTestScoreResultList(param, request.getSession().getAttribute("user_id").toString());
    }
}
