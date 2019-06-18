package com.oj.controller.testing;

import com.oj.service.testing.CaseService;
import com.oj.entity.testing.Case;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouli
 */
@Controller
@RequestMapping("/caseMn")
public class CaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CaseService caseService;

    @PostMapping("getCase")
    @ResponseBody
    public List<Map> getCase(HttpServletRequest request){
        List<Map> p = caseService.getCase(request.getParameter("id"));
        return p;
    }

    @PostMapping("caseDelete")
    @ResponseBody
    public Map<String, String> problemDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            caseService.CaseDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("/saveOrUpdateCase")
    @ResponseBody
    public Map<String, String> saveOrUpdateCase( @RequestBody List<Case> cases){
        Case[] cs = cases.toArray(new Case[cases.size()]);
        Map<String, String> map = new HashMap<>();
        try {
            caseService.saveOrUpadateCase(cs);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("caseDeleteByProblem")
    @ResponseBody
    public Map<String, String> problemDeleteByProblem(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            caseService.CaseDeleteByProblem(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }
}
