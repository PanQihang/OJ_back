package com.oj.controller.competition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.oj.service.competition.CompetitionService;
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
 * @author li
 * @Time 2019年4月1日 10点18分
 * @Description 实验管理controller类
 */
@Controller
@RequestMapping("/competition")
public class CompetitionController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private TestService testService;
    @RequestMapping("/")
    public String index(){

        return "competition/competition";
    }

    //获取竞赛信息
    @PostMapping("/getcompetitionInfo")
    @ResponseBody
    public List<Map> getcompetitionInfo(@RequestBody Map<String, Object> param,HttpServletRequest request){
        String experName = param.get("experName").toString();
        System.out.println(experName);
        Map<String, List> map = new HashMap<>();

        return competitionService.getcompetitionInfo(experName);
    }
    @RequestMapping("/addCom/{id}")
    public String addCom(@PathVariable String id, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", id);
        //如果为编辑，加入被编辑的实验的参数
        if (!id.equals("add")){
            //实验信息
            info.put("compInfo", competitionService.getCompInfoById(id));
            //已选择试题信息
            info.put("selectedQueList", competitionService.getSelectedQueListById(id));
            info.put("selectedJroomList", competitionService.getSelectedJroomListById(id));
        }
        model.addAttribute("info", info);
        return "competition/addComp";
    }
    //获取试题列表
    @PostMapping("/loadPreSelectQuestion")
    @ResponseBody
    public List<Map> loadPreSelectQuestion(){
        return competitionService.loadPreSelectQuestion();
    }


    //获取机房列表
    @PostMapping("/loadPreSelectJroom")
    @ResponseBody
    public List<Map> loadPreSelectJroom(){
        return competitionService.loadPreSelectJroom();
    }

    //更新或新增实验
    @PostMapping("/saveOrUpdateComp")
    @ResponseBody
    public Map saveOrUpdateComp(@RequestBody Map<String, Object> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            competitionService.saveOrUpdateComp(param, request.getSession().getAttribute("user_id").toString());
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
    @PostMapping("/compDelete")
    @ResponseBody
    public Map<String, String> compDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
           competitionService.compDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }
    @RequestMapping("/showcompScore/{tid}")
    public String showcompScore(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        info.put("tid", tid);
        model.addAttribute("info", info);
        return "competition/compScore";
    }
    //获取考试相似度判断结果页面
    @RequestMapping("/similarityUser/{tid}")
    public String showcompSimilarityUser(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        info.put("tid", tid);
        model.addAttribute("info", info);
        return "competition/similarityUser";
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

    //获取考试成绩集
    @PostMapping("/getcompScoreResultList")
    @ResponseBody
    public List<Map> getcompScoreResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        //System.out.println(param);
        //System.out.println(request.getSession().getAttribute("user_id").toString());
        return testService.getcompScoreResultList(param, request.getSession().getAttribute("user_id").toString());
    }


}
