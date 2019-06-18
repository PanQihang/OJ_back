package com.oj.service.serviceImpl.exam;


import com.oj.entity.exam.Test;
import com.oj.entity.other.OJTimerCell;
import com.oj.entity.other.OJTimerLink;
import com.oj.mapper.exam.TestMapper;
import com.oj.service.exam.TestService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static java.lang.System.out;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理Service接口实现类
 */
@Service
public class TestServicelmpl implements TestService {

    @Autowired(required = false)
    private TestMapper mapper;

    /**
     * 获取当前教师负责班级下的考试列表接口功能实现
     * @param testName
     * @param user_id
     * @return
     */
    @Override
    public List<Map> getTestInfo(String testName, String user_id) {
        Map<String, String> params = new HashMap<>();
        params.put("testName", testName);
        params.put("user_id", user_id);

        //*****************************************************************************************************************************************************
        //FunctionPQH("319");
        List<Map> list = mapper.getTestInfo(params);
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
     * 通过ID获取当前考试信息接口功能实现
     * @param id
     * @return
     */
    @Override
    public Map getTestInfoById(String id) {
        return mapper.getTestInfoById(id);
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
     * 更新或新增考试接口功能实现
     * @param param
     */
    @Transactional
    @Override
    public void saveOrUpdateTest(Map<String, Object> param, String user_id) throws Exception {
        //获取考试信息
        JSONObject obj = JSONObject.fromObject(param.get("testInfo"));
        Test testInfo = (Test)JSONObject.toBean(obj, Test.class);
        out.println("TESTINFO---->");
        out.println(testInfo);
        //获取考试已选择试题信息
        List<Map<String, String>>  selectedQueList = (List<Map<String, String>>)param.get("selectedQueList");
        //获取考试已选择班级信息
        List<Map<String, String>>  selectedClassList = (List<Map<String, String>>)param.get("selectedClassList");
        //获取考试已选择机房信息
        List<Map<String, String>>  selectedJroomList = (List<Map<String, String>>)param.get("selectedJroomList");
        //如果id为add，则进行添加操作
        if ("add".equals(param.get("id"))){
            testInfo.setAdminId(user_id);
            testInfo.setReport("0");
            testInfo.setKind("1");
            mapper.saveTest(testInfo);
            String testId = testInfo.getId();
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("testId", testId);
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedClass:selectedClassList) {
                selectedClass.put("testId", testId);
                mapper.saveSelectedClass(selectedClass);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("testId", testId);
                mapper.saveSelectedJroom(selectedJroom);
            }
        }else{
            testInfo.setId(param.get("id").toString());
            mapper.deleteQue(testInfo.getId());
            mapper.deleteClass(testInfo.getId());
            mapper.deleteJroom(testInfo.getId());
            mapper.updateTest(testInfo);
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("testId", testInfo.getId());
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedClass:selectedClassList) {
                selectedClass.put("testId", testInfo.getId());
                mapper.saveSelectedClass(selectedClass);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("testId", testInfo.getId());
                mapper.saveSelectedJroom(selectedJroom);
            }
        }
        OJTimerLink.resestTimerLink();

    }

    /**
     * 通过ID删除考试接口功能实现
     * @param id
     */
    @Transactional
    @Override
    public void testDelete(String id) {
        //删除绑定试题
        mapper.deleteQue(id);
        //删除绑定班级
        mapper.deleteClass(id);
        //删除绑定机房
        mapper.deleteJroom(id);
        //删除考试信息
        mapper.deleteTest(id);
    }

    //*********************************************************************************************************** xln
    /*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */
    //获取指定的考试成绩
    public List<Map> getTestScoreResultList(Map<String, String> param, String user_id){
        param.put("user_id", user_id);
        return mapper.getTestScoreResult(param);
    }
    public List<Map> getcompScoreResultList(Map<String, String> param, String user_id){
        //param.put("user_id", user_id);
        return mapper.getcompScoreResult(param);
    }
    //获取指定考试的简要信息
    public Map getTestBriefInf(String id){
        return mapper.getTestBriefInf(id);
    }
    //获取本次考试下的所有班级
    public List<Map> getTestClassList(String id){
        return mapper.getTestClassList(id);
    }
    //获取考试题目集
    public List<Map> getTestProblemList(String id){
        return mapper.getTestProblemList(id);
    }
    //获取考试结果统计结果
    public List<Map> getTheStatisticalResult(Map<String, String> param){
        return  mapper.getTheStatisticalResult(param);
    }
    //获取本次考试下的所有专业



    //*********************************************** 定时任务-（实验/考试）结果统计
    //获取考试的提交的汇总情况
    public List<Map<String, Object>> getSubmitStateResult(String params){
        return mapper.getSubmitStateResult(params);
    }
    //获取题目与分数
    public List<Map<String, Object>> getTestProblemInf(String testId){
        return mapper.getTestProblemInf(testId);
    }
    //删除指定的考试结果集
    public void deleteTargetTestResult(String testId){
        mapper.deleteTargetTestResult(testId);
    }
    //保存给定的考试结果集
    public void saveTestResultList(List<Map> data){
            mapper.saveTheStudentTestResult(data);
    }
    //获取当前正在进行的（实验/考试）
    public List<Map> getCurrentTestList(){
        return mapper.getCurrentTestList();
    }
    //获取正在进行的（实验），并整理当前已有的提交状态，汇总后覆盖原数据
    public void RunDoIt(){
        List<Map> list = getCurrentTestList();
        for(Map<String, Object> temp : list){
            FunctionLY(temp.get("testId").toString());
        }
    }
    //整理指定考试的结果集，并覆盖原先数据
    //（应考虑是否保存最新结果集到本地磁盘，防止极端概率事件发生。如在删除原有数据库数据后，新数据还未完全存入数据库，此时突然断电！answer:给前端一个接口，支持手动判分）
    public boolean FunctionLY (String testId){

        if(true) return  FunctionLYplus(testId);
        class functionZT { //运算函数-取名ZT（取名郑通，望借大佬之势，镇压一切bug，护我OJ百世荣昌！）
            public int get(List<Map<String, Object>> list, String tag){
                for(Map<String, Object> temp : list){
                    if(tag.equals(temp.get("pid").toString())){
                        //System.out.println("##"+temp.get("score").toString());
                        return Integer.parseInt(temp.get("score").toString());
                    }
                }
                return 0;
            }
        }
        functionZT zt = new functionZT();
        Map map = new HashMap<String, String>();
        map.put("testId", testId);
        List<Map<String, Object>> subStateResultList = getSubmitStateResult(testId); //获取当前提交状态的汇总结果集
        List<Map<String, Object>> testProInfList = getTestProblemInf(testId); //获取题目与对应分数的汇总集

        Map<String, String> t = new HashMap<String, String>();
        List<Map> result = new LinkedList<Map>();
        String scoreResult = "";
        int all=0;
        for(Map<String, Object> temp : subStateResultList){
            String str = t.get("account");
            if(null==str || !temp.get("account").equals(str)){
                if(null!=str){
                    t.put("all", ""+all);
                    t.put("result", scoreResult);
                    t.put("first_ip", "000.000.000.000");
                    t.put("testId", testId);
                }
                t = new HashMap<String, String>();
                t.put("sid", temp.get("id").toString());
                t.put("account", temp.get("account").toString());
                t.put("name", temp.get("name").toString());
                t.put("class", temp.get("class").toString());
                t.put("class_id", temp.get("class_id").toString());
               // System.out.println(temp.get("accuracy").toString()+" * "+ zt.get(testProInfList, temp.get("problem_id").toString()));
                all=(int)(zt.get(testProInfList, temp.get("problem_id").toString()) * Double.parseDouble(temp.get("accuracy").toString()));
                scoreResult=temp.get("problem_id")+":"+String.format("%.0f", (zt.get(testProInfList, temp.get("problem_id").toString()) * Double.parseDouble(temp.get("accuracy").toString())))+";";
                result.add(t);
                continue;
            }
            all+=(int)(zt.get(testProInfList, temp.get("problem_id").toString()) * Double.parseDouble(temp.get("accuracy").toString()));
            scoreResult+=temp.get("problem_id")+":"+String.format("%.0f", (zt.get(testProInfList, temp.get("problem_id").toString()) * Double.parseDouble(temp.get("accuracy").toString())))+";";
        }
        t.put("all", ""+all);
        t.put("result", scoreResult);
        t.put("first_ip", "000.000.000.000");
        t.put("testId", testId);
        if(0==result.size()) return true;
        deleteTargetTestResult(testId);
        saveTestResultList(result);
        return true;
    }
    public boolean FunctionLYplus(String testId){

        Map map = new HashMap<String, String>();
        map.put("testId", testId);
        List<Map<String, Object>> subStateResultList = getSubmitStateResult(testId); //获取当前提交状态的汇总结果集
        List<Map<String, Object>> testProInfList = getTestProblemInf(testId); //获取题目与对应分数的汇总集
        Map<String, Integer> problemInf = new HashMap();
        testProInfList.forEach(cell->{
            problemInf.put(cell.get("pid").toString(), Integer.parseInt(cell.get("score").toString()));
        });
        Map<String, Map> temp = new HashMap();
        subStateResultList.forEach(cell ->{
            String stuId, proId;
            Double accuracy;
            Map tMap;
            stuId = cell.get("account").toString();
            proId = cell.get("problem_id").toString();
            accuracy = Double.parseDouble(cell.get("accuracy").toString());
            int proScore = (int)(problemInf.get(proId) * accuracy); //一个题目的分数
            tMap = temp.get(stuId);
            if(null == tMap){
                tMap = new HashMap();
                tMap.put("testId", testId); //考试编号
                tMap.put("first_ip", "000.000.000.000");
                tMap.put("sid", cell.get("id").toString()); //学生编号
                tMap.put("name", cell.get("name").toString()); //学生姓名
                tMap.put("account", stuId); //学生学号
                tMap.put("class", cell.get("class").toString()); //班级名称
                tMap.put("class_id", cell.get("class_id").toString()); //班级编号
                tMap.put("all", ""+proScore); //考试总分
                tMap.put("result", proId+":"+proScore+";"); //考试结果
                temp.put(stuId, tMap);
            }else{
                Integer k = Integer.parseInt(tMap.get("all").toString());
                tMap.put("all", ""+(k+proScore));
                tMap.put("result", tMap.get("result")+proId+":"+proScore+";");
            }
        });
        List<Map> result = new LinkedList<Map>(temp.values());
       // out.println("-------------------------");
       // result.forEach(cell->out.println(cell));
        if(0==result.size()) return true;
        deleteTargetTestResult(testId);
        saveTestResultList(result);
        return true;
    }

    //删除指定考试里指定学生的ip绑定数据
    public boolean deleteTargetIpData(Map<String, String> param){
        String Id = param.get("Id");
        if(null==Id) return false;
        mapper.deleteTargetIpData(Id);
        return true;
    }


    //*********************************************** 定时任务-（实验/考试）相似结果统计
    //获取指定（实验/考试）相似判断结果
    public List<Map> getSimilarityResult(String testId){
        return mapper.getSimilarityResult(testId);
    }
    //获取指定的两个提交编号的代码
    public Map getTargetSubmitCode(Map t){
        return mapper.getTargetSubmitCode(t);
    }
    //获取指定考试的提交数据（代码、学生信息）
    public List<Map<String, Object>> getSubmitCodeAndStuInf(String testId){
        return mapper.getSubmitCodeAndStuInf(testId);
    }
    //处理考试提交代码，判断相似度，并保存到数据库
    public boolean FunctionPQH(String testId){
        List<Map<String, Object>> list = getSubmitCodeAndStuInf(testId);

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> temp = null;
        Map<String, Object> t = null;
        int i, f, j ;

        for(i=0, f=0; i<list.size(); i++){
            t = list.get(i);
            for(j=f;j<i;j++){
                Map<String, Object> map = list.get(j);
                if(!t.get("problem_id").toString().equals(map.get("problem_id").toString())){ //非同一编号的题目
                    f=i; break;
                }else if(t.get("user_id").toString().equals(map.get("user_id").toString())){ //同一用户的重复提交
                    break;
                }else{ //非同一用户的同一题目的提交，（）
                    //相似比较
                    String codeA = t.get("submit_code").toString(); //更后提交的人的代码
                    String codeB = map.get("submit_code").toString(); //先提交人的代码
                    codeA.replace(" ", "");
                    codeA.replace("\n", "");
                    codeB.replace(" ", "");
                    codeB.replace("\n", "");
                    if(codeA.equals(codeB)){
                        temp = new HashMap<String, String>();
                        temp.put("problem_id", t.get("problem_id").toString());
                        temp.put("tid", testId);
                        temp.put("f_sid", map.get("id").toString());
                        temp.put("f_userid", map.get("user_id").toString());
                        temp.put("s_sid", t.get("id").toString());
                        temp.put("s_userid", t.get("user_id").toString());
                        temp.put("similarity", "100");//两代码的最终相似度
                        result.add(temp);
                        break;
                    }
                }
            }
        }
        if(0==result.size()) return true;
        mapper.saveTheSimilarityResult(result);
        return true;
    }

}
