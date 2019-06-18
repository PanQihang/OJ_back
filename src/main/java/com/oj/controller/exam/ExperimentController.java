package com.oj.controller.exam;

import com.oj.service.exam.ExperimentService;
import com.oj.service.exam.TestService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 实验管理controller类
 */
@Controller
@RequestMapping("/experimentMn")
public class ExperimentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private TestService testService;
    @RequestMapping("/")
    public String index(){
        System.out.println("获取时间" + new Date());

        return "exam/experiment";
    }
    //展示实验编辑页面
    @RequestMapping("/addExper/{id}")
    public String addExper(@PathVariable String id, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", id);
        //如果为编辑，加入被编辑的实验的参数
        if (!id.equals("add")){
            //实验信息
            info.put("experInfo", experimentService.getExperInfoById(id));
            //已选择试题信息
            info.put("selectedQueList", experimentService.getSelectedQueListById(id));
            //已选择班级信息
            info.put("selectedClassList", experimentService.getSelectedClassListById(id));
            //已选择机房信息
            info.put("selectedJroomList", experimentService.getSelectedJroomListById(id));
        }
        model.addAttribute("info", info);
        return "exam/addExper";
    }
    @RequestMapping("/copyExper/{id}")
    //@ResponseBody
    public String copyExper(@PathVariable String id, Model model,HttpServletRequest request){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", "add");
        //如果为编辑，加入被编辑的实验的参数
            //实验信息
            info.put("experInfo", experimentService.getExperInfoById(id));
            //已选择试题信息
            info.put("selectedQueList", experimentService.getSelectedQueListById(id));
            //已选择班级信息
            info.put("selectedClassList", experimentService.loadPreSelectClass(request.getSession().getAttribute("user_id").toString()));
            //已选择机房信息
            //info.put("selectedJroomList", experimentService.getSelectedJroomListById(id));

        model.addAttribute("info", info);
        return "exam/copyExper";
    }
    @RequestMapping("/showIp/{tid}")
    public String showIp(@PathVariable String tid, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("tid",tid);
        model.addAttribute("info", info);
        return "exam/showIp";
    }
    @PostMapping("/getIpInfoList")
    @ResponseBody
    public List<Map> getIpInfoList(@RequestBody Map<String, String> param, HttpServletRequest request){
        //String experName = request.getParameter("experName");
        return experimentService.getIpInfoById(param);
    }
    //获取实验信息
    @PostMapping("/getExperInfo")
    @ResponseBody
    public Map getExperInfo(HttpServletRequest request){
        String experName = request.getParameter("experName");
        Map<String, List> map = new HashMap<>();
        map.put("data", experimentService.getExperInfo(experName, request.getSession().getAttribute("user_id").toString()));
        return map;
    }

    //获取试题列表
    @PostMapping("/loadPreSelectQuestion")
    @ResponseBody
    public List<Map> loadPreSelectQuestion(){
        return experimentService.loadPreSelectQuestion();
    }

    //获取班级列表
    @PostMapping("/loadPreSelectClass")
    @ResponseBody
    public List<Map> loadPreSelectClass(HttpServletRequest request){
        return experimentService.loadPreSelectClass(request.getSession().getAttribute("user_id").toString());
    }

    //获取机房列表
    @PostMapping("/loadPreSelectJroom")
    @ResponseBody
    public List<Map> loadPreSelectJroom(){
        return experimentService.loadPreSelectJroom();
    }

    //更新或新增实验
    @PostMapping("/saveOrUpdateExper")
    @ResponseBody
    public Map saveOrUpdateExper(@RequestBody Map<String, Object> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            experimentService.saveOrUpdateExper(param, request.getSession().getAttribute("user_id").toString());
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    //
    @PostMapping("/experDelete")
    @ResponseBody
    public Map<String, String> experDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            experimentService.experDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }
    @RequestMapping("/showTestScore/{tid}")
    public String showTestScore(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        info.put("tid", tid);
        model.addAttribute("info", info);
        return "exam/testScore";
    }
    //获取考试相似度判断结果页面
    @RequestMapping("/similarityUser/{tid}")
    public String showTestSimilarityUser(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        info.put("tid", tid);
        model.addAttribute("info", info);
        return "exam/similarityUser";
    }
    //获取考试简要信息
    @RequestMapping("/getTestBriefInf")
    @ResponseBody
    public Map getTestBriefInf(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map map = testService.getTestBriefInf(param.get("testId"));
        System.out.println(map);
        return map;
    }
    //获取考试题目集
    @RequestMapping("/getTestProblemList")
    @ResponseBody
    public List<Map> getTestProblemList(@RequestBody Map<String, String> param, HttpServletRequest request){
        return testService.getTestProblemList(param.get("testId"));
    }
    //获取考试结果统计结果
    @RequestMapping("/getTheStatisticalResult")
    @ResponseBody
    public Map getTheStatisticalResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map result = new HashMap<String, Object>();
        List<Map> list = testService.getTheStatisticalResult(param);
        List keyList = new LinkedList<String>();
        for(Map k : list){
            k.put("name", k.get("name")+"分");
            keyList.add(k.get("name"));
        }
        result.put("key", keyList);
        result.put("data", list);
        return result;
    }
    //获取本次考试下的所有班级
    @RequestMapping("/getTestClassList")
    @ResponseBody
    public List<Map> getTestClassList(@RequestBody Map<String, String> param, HttpServletRequest request){
        return testService.getTestClassList(param.get("testId"));
    }
    //获取考试成绩集
    @PostMapping("/getTestScoreResultList")
    @ResponseBody
    public List<Map> getTestScoreResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        //System.out.println(param);
        //System.out.println(request.getSession().getAttribute("user_id").toString());
        return testService.getTestScoreResultList(param, request.getSession().getAttribute("user_id").toString());
    }
    //获取本次考试下的所有专业

}
