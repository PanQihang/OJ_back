package com.oj.service.serviceImpl.competition;
import com.oj.entity.competition.Competition;
import com.oj.mapper.competition.CompetitionMapper;
import com.oj.service.competition.CompetitionService;
import com.oj.entity.other.OJTimerLink;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CompetitionServicelmpl implements CompetitionService {

    @Autowired(required = false)
    private CompetitionMapper mapper;

    @Override
    public List<Map> getcompetitionInfo(String experName){
        Map<String, String> params = new HashMap<>();
        params.put("experName", experName);
        List<Map> list = mapper.getcompetitionInfo(params);
        for (Map map:list) {
            map.put("start_time", timeStamp2String((Timestamp)map.get("start_time")));
            map.put("end_time", timeStamp2String((Timestamp)map.get("end_time")));
        }
        return list;
    }

    public String timeStamp2String(Timestamp timestamp){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            String str2 = sdf.format(timestamp);
            return str2;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * 获取题目列表接口功能实现
     * @return
     */
    @Override
    public List<Map> loadPreSelectQuestion() {
        return mapper.loadPreSelectQuestion();
    }
    /**
     * 获取机房列表接口功能实现
     * @return
     */
    @Override
    public List<Map> loadPreSelectJroom() {
        return mapper.loadPreSelectJroom();
    }

    /**
     * 通过ID获取当前实验信息接口功能实现
     * @param id
     * @return
     */
    @Override
    public Map getCompInfoById(String id) {
        return mapper.getCompInfoById(id);
    }

    /**
     * 通过ID获取已选择试题列表接口功能实现
     * @param id
     * @return
     */
    @Override
    public List<Map> getSelectedQueListById(String id) {
        return mapper.getSelectedQueListById(id);
    }



    /**
     * 通过ID获取已选择机房列表接口功能实现
     * @param id
     * @return
     */
    @Override
    public List<Map> getSelectedJroomListById(String id) {
        return mapper.getSelectedJroomListById(id);
    }

    @Transactional
    @Override
    public void saveOrUpdateComp(Map<String, Object> param, String user_id) throws Exception {
        //获取实验信息
        JSONObject obj = JSONObject.fromObject(param.get("experInfo"));
        Competition compInfo = (Competition)JSONObject.toBean(obj, Competition.class);
        //获取实验已选择试题信息
        List<Map<String, String>>  selectedQueList = (List<Map<String, String>>)param.get("selectedQueList");
     //获取实验已选择机房信息
        List<Map<String, String>>  selectedJroomList = (List<Map<String, String>>)param.get("selectedJroomList");
        //如果id为add，则进行添加操作
        if ("add".equals(param.get("id"))){
            compInfo.setAdminId(user_id);
            compInfo.setReport("0");
            compInfo.setKind("4");
            mapper.saveComp(compInfo);
            String experId = compInfo.getId();
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("experId", experId);
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("experId", experId);
                mapper.saveSelectedJroom(selectedJroom);
            }
        }else{
            compInfo.setId(param.get("id").toString());
            mapper.deleteQue(compInfo.getId());
            mapper.deleteJroom(compInfo.getId());
            mapper.updateComp(compInfo);
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("experId", compInfo.getId());
                mapper.saveSelectedQue(selectedQue);
            }

            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("experId", compInfo.getId());
                mapper.saveSelectedJroom(selectedJroom);
            }
        }
        OJTimerLink.resestTimerLink();
    }
    /**
     * 通过ID删除实验接口功能实现
     * @param id
     */
    @Transactional
    @Override
    public void compDelete(String id) {
        //删除绑定试题
        mapper.deleteQue(id);
        //删除绑定机房
        mapper.deleteJroom(id);
        //删除实验信息
        mapper.deleteComp(id);
    }

}
