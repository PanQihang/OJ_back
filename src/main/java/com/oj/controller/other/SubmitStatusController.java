package com.oj.controller.other;

import com.oj.entity.other.SubmitStatus;
import com.oj.frameUtil.JqueryDataTableDto;
import com.oj.service.other.SubmitStatusService;
import net.sf.json.JSON;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/submitStatusMn")
public class SubmitStatusController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    //    依赖注入UserSubjectService
    @Autowired
    private SubmitStatusService submitstatusService;

    //返回主题管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        return "other/submitStatus";
    }

    //通过调用接口传递的条件，返回对应的主题信息JsonList
    @RequestMapping("/getSubmitStatusMaplist")
    @ResponseBody
    public String getSubmitStatusMaplist(Model model, HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String count = request.getParameter("length");
        String problem_id = request.getParameter("problem_id");
        String account = request.getParameter("account");
        String submit_state = request.getParameter("submit_state");
        JqueryDataTableDto jqueryDataTableDto = submitstatusService.getSubmitStatusMaplist(start, count, problem_id, account, submit_state);
        return net.sf.json.JSONObject.fromObject(jqueryDataTableDto).toString();
    }
}
