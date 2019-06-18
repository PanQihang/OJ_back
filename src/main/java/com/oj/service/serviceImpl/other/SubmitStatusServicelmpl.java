package com.oj.service.serviceImpl.other;
import com.oj.entity.other.SubmitCodeList;
import com.oj.entity.other.SubmitStatus;
import com.oj.frameUtil.JqueryDataTableDto;
import com.oj.mapper.other.SubmitStatusMapper;
import com.oj.service.other.SubmitStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SubmitStatusServicelmpl implements SubmitStatusService{
    @Autowired(required = false)
    private SubmitStatusMapper mapper;

    @Override
    public JqueryDataTableDto getSubmitStatusMaplist(String start, String count, String problem_id, String account, String submit_state)  {
        Map<String, String> params = new HashMap<>();
        params.put("start", start);
        params.put("count", count);
        params.put("problem_id", problem_id);
        params.put("account", account);
        params.put("submit_state", submit_state);
        JqueryDataTableDto jqueryDataTableDto=new JqueryDataTableDto();
        List<SubmitCodeList> list = mapper.getSubmitStatusMaplist(params);
        int total = mapper.selectTotalCount();
        int filterTotal = mapper.selectRecordsFiltered(params);
        jqueryDataTableDto.setRecordsTotal(total);
        jqueryDataTableDto.setRecordsFiltered(filterTotal);
        jqueryDataTableDto.setData(list);
        return jqueryDataTableDto;
    }
}
