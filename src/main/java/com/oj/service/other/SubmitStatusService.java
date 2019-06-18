package com.oj.service.other;
import com.oj.entity.other.SubmitStatus;
import com.oj.frameUtil.JqueryDataTableDto;

import java.util.List;
import java.util.Map;

public interface SubmitStatusService {
    //获取主题信息列表
    public JqueryDataTableDto getSubmitStatusMaplist(String start, String count, String problem_id, String account, String submit_state);
}
