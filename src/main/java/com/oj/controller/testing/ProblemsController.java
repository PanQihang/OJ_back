package com.oj.controller.testing;

import com.oj.entity.testing.Problem;
import com.oj.service.testing.ProblemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouli
 * Time 2019年4月1日 11点24分
 * Description
 */

@Controller
@RequestMapping("/problemsMn")
public class ProblemsController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    //依赖注入ProblemService
    @Autowired
    private ProblemsService problemsService;


    @RequestMapping("/")
    //返回question.html页面
    public String index(ModelMap modelMap, HttpServletRequest request) {
        return "testing/problems";
    }

    @PostMapping("/getProblemsMapList")
    @ResponseBody
    //通过调用接口传递的条件，返回对应的题目信息JsonList
    public List<Map> getProblemsMapList(@RequestBody Map<String, String> param, HttpServletRequest request) {
        return problemsService.getProblemsMapList(param);

    }

    @PostMapping("/problemDelete")
    @ResponseBody
    public Map<String, String> problemDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            problemsService.problemDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @RequestMapping("/analyze")
    //返回analyzeProblem.html
    public String analyze( HttpServletRequest request) {
        return "testing/analyzeProblem";
    }

    @RequestMapping("/problemDetails")
    //返回analyzeProblem.html
    public String problemDetails( HttpServletRequest request) {
        return "testing/problemDetails";
    }


    @RequestMapping("/editProblem")
    //返回editProblem.html
    public String editProblem( HttpServletRequest request) {
        return "testing/editProblem";
    }

    @PostMapping("/saveOrUpdateProblem")
    @ResponseBody
    public Map<String, String> saveOrUpdateProblem(@RequestBody Problem problem){
        Map<String, String> map = new HashMap<>();
        try {
            problemsService.saveOrUpadateProblem(problem);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("getProblemDetails")
    @ResponseBody
    public List<Map> getProblemDetails(HttpServletRequest request){
        List<Map> p = problemsService.getProblemDetails(request.getParameter("id"));
        return p;
    }
    @PostMapping("analyzeProblem")
    @ResponseBody
    public List<Map> analyzeProblem(HttpServletRequest request){
        return problemsService.analyzeProblem(request.getParameter("id"));
    }
}
