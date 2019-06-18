package com.oj.service.serviceImpl.exam;

import com.oj.entity.exam.Experiment;
import com.oj.entity.other.OJTimerLink;
import com.oj.mapper.exam.ExperimentMapper;
import com.oj.service.exam.ExperimentService;
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

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 实验管理Service接口实现类
 */
@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Autowired(required = false)
    private ExperimentMapper mapper;

    /**
     * 获取当前教师负责班级下的实验列表接口功能实现
     * @param experName
     * @param user_id
     * @return
     */
    @Override
    public List<Map> getExperInfo(String experName, String user_id) {
        Map<String, String> params = new HashMap<>();
        params.put("experName", experName);
        params.put("user_id", user_id);
        List<Map> list = mapper.getExperInfo(params);
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
     * 获取班级列表接口功能实现
     * @return
     */
    @Override
    public List<Map> loadPreSelectClass(String user_id) {
        return mapper.loadPreSelectClass(user_id);
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
    public Map getExperInfoById(String id) {
        return mapper.getExperInfoById(id);
    }
    @Override
    public List<Map> getIpInfoById(Map<String, String> param) {
        return mapper.getIpInfoById(param);
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
     * 通过ID获取已选择班级列表接口功能实现
     * @param id
     * @return
     */
    @Override
    public List<Map> getSelectedClassListById(String id) {
        return mapper.getSelectedClassListById(id);
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

    /**
     * 更新或新增实验接口功能实现
     * @param param
     */
    @Transactional
    @Override
    public void saveOrUpdateExper(Map<String, Object> param, String user_id) throws Exception {
        //获取实验信息
        JSONObject obj = JSONObject.fromObject(param.get("experInfo"));
        Experiment experInfo = (Experiment)JSONObject.toBean(obj, Experiment.class);
        //获取实验已选择试题信息
        List<Map<String, String>>  selectedQueList = (List<Map<String, String>>)param.get("selectedQueList");
        //获取实验已选择班级信息
        List<Map<String, String>>  selectedClassList = (List<Map<String, String>>)param.get("selectedClassList");
        //获取实验已选择机房信息
        List<Map<String, String>>  selectedJroomList = (List<Map<String, String>>)param.get("selectedJroomList");
        //如果id为add，则进行添加操作
        if ("add".equals(param.get("id"))){
            experInfo.setAdminId(user_id);
            experInfo.setReport("0");
            experInfo.setKind("2");
            mapper.saveExper(experInfo);
            String experId = experInfo.getId();
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("experId", experId);
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedClass:selectedClassList) {
                selectedClass.put("experId", experId);
                mapper.saveSelectedClass(selectedClass);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("experId", experId);
                mapper.saveSelectedJroom(selectedJroom);
            }
        }else{
            experInfo.setId(param.get("id").toString());
            mapper.deleteQue(experInfo.getId());
            mapper.deleteClass(experInfo.getId());
            mapper.deleteJroom(experInfo.getId());
            mapper.updateExper(experInfo);
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("experId", experInfo.getId());
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedClass:selectedClassList) {
                selectedClass.put("experId", experInfo.getId());
                mapper.saveSelectedClass(selectedClass);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("experId", experInfo.getId());
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
    public void experDelete(String id) {
        //删除绑定试题
        mapper.deleteQue(id);
        //删除绑定班级
        mapper.deleteClass(id);
        //删除绑定机房
        mapper.deleteJroom(id);
        //删除实验信息
        mapper.deleteExper(id);
    }
}
