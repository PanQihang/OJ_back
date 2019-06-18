package com.oj.mapper.exam;

import com.oj.entity.exam.Test;
import com.oj.mapper.provider.exam.TestProvider;
import com.oj.mapper.provider.system.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理Mapper接口
 */
@Mapper
public interface TestMapper {

    //获取考试信息列表
    @SelectProvider(type=TestProvider.class, method = "getTestInfoSql")
    public List<Map> getTestInfo(@Param("condition")Map<String, String> params);

    //获取试题信息列表
    @Select("SELECT id, name, IF(public='on','是','否') as isShow FROM teach_problems where name !='' ORDER BY id DESC")
    public List<Map> loadPreSelectQuestion();

    //获取班级列表
    @Select("SELECT b.id, b.name FROM teach_course_class a INNER JOIN teach_class b on a.class_id = b.id  " +
            "WHERE course_id in (SELECT course_id FROM teach_admin_course " +
            "WHERE admin_id = #{user_id}) ORDER BY b.id ")
    public List<Map> loadPreSelectClass(@Param("user_id") String user_id);

    //获取机房列表
    @Select("SELECT id, location, ip FROM teach_ip ORDER BY id DESC")
    public List<Map> loadPreSelectJroom();

    //通过ID获取考试信息
    @Select("SELECT id, name, FROM_UNIXTIME(start) as start, FROM_UNIXTIME(end) as end, is_ip, only_ip, description  FROM teach_test WHERE id = #{id}")
    public Map getTestInfoById(String id);
    //通过ID获得IP信息
    @SelectProvider(type=TestProvider.class, method = "getIpInfoSql")
    public List<Map> getIpInfoById(@Param("condition")Map<String, String> params);
    //通过ID获取已选择试题信息
    @Select("SELECT b.id, b.name, a.score FROM teach_test_problems a INNER JOIN teach_problems b on a.pid = b.id WHERE a.tid = #{id} ORDER BY b.id")
    public List<Map> getSelectedQueListById(String id);

    //通过ID获取已选择班级信息
    @Select("SELECT b.id, b.name FROM teach_test_class a INNER JOIN teach_class b on a.class_id = b.id WHERE a.test_id = #{id} ORDER BY b.id")
    public List<Map> getSelectedClassListById(String id);

    //通过ID获取已选择机房信息
    @Select("SELECT b.id, b.location, b.ip FROM teach_test_ip a INNER JOIN teach_ip b on a.iid = b.id where a.tid = #{id} ORDER BY b.id")
    public List<Map> getSelectedJroomListById(String id);

    @Insert("INSERT INTO teach_test (NAME, START, END, description, kind, is_ip, only_ip, report, admin_id) " +
            "VALUES(#{name},UNIX_TIMESTAMP(#{start}),UNIX_TIMESTAMP(#{end}),#{description},#{kind},#{isIp},#{onlyIp},#{report},#{adminId})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void saveTest(Test testInfo);

    @Insert("INSERT INTO teach_test_problems(tid, pid, score) VALUES(#{testId}, #{id}, #{score})")
    public void saveSelectedQue(Map<String,String> selectedQue);

    @Insert("INSERT INTO teach_test_class(test_id, class_id) VALUES(#{testId}, #{id})")
    public void saveSelectedClass(Map<String,String> selectedClass);

    @Insert("INSERT INTO teach_test_ip(tid, iid) VALUES(#{testId}, #{id})")
    public void saveSelectedJroom(Map<String,String> selectedJroom);

    @Delete("DELETE FROM teach_test_problems where tid = #{id}")
    public void deleteQue(String id);

    @Delete("DELETE FROM teach_test_class where test_id = #{id}")
    public void deleteClass(String id);

    @Delete("DELETE FROM teach_test_ip where tid = #{id}")
    public void deleteJroom(String id);

    @Delete("DELETE FROM teach_test where id = #{id}")
    public void deleteTest(String id);

    @Update("UPDATE teach_test SET name=#{name}, start=UNIX_TIMESTAMP(#{start}), end=UNIX_TIMESTAMP(#{end}), description=#{description}, only_ip=#{onlyIp}, is_ip=#{isIp} where id=#{id}")
    public void updateTest(Test testInfo);
    //************************************************************************************************** xln
    /*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */
    //获取指定考试的成绩结果
    @SelectProvider(type=TestProvider.class, method = "getTestScoreResultList")
    public List<Map> getTestScoreResult(@Param("condition")Map<String, String> params);
    @SelectProvider(type=TestProvider.class, method = "getcompScoreResultList")
    public List<Map> getcompScoreResult(@Param("condition")Map<String, String> params);
    //获取指定考试的简要信息
    @Select("SELECT t.name 'testName', FROM_UNIXTIME(t.start) testSDate, FROM_UNIXTIME(t.end) testEDate FROM teach_test t WHERE t.id = #{id}")
    public Map getTestBriefInf(String id);
    //获取本次考试下的所有班级
    @Select("SELECT t.class_id classId, a.name 'name' FROM teach_test_class t, teach_class a WHERE t.test_id = #{id} AND t.class_id = a.id order by t.class_id")
    public List<Map> getTestClassList(String id);
    //获取考试题目集
    @Select("SELECT t.id id, t.name 'name', a.score FROM teach_test_problems a, teach_problems t WHERE a.tid = #{id} AND t.id=a.pid order by t.id")
    public List<Map> getTestProblemList(String id);
    //获取考试结果统计结果
    @SelectProvider(type=TestProvider.class, method="getStatisticalResultSQL")
    public List<Map> getTheStatisticalResult(@Param("condition")Map<String, String> params);
    //获取本次考试下的所有专业


    //获取指定考试下所有的考生列表
    //获取指定考试下指定考生的题目完成情况

    //*********************************************** 定时任务-（实验/考试）结果统计
    //获取

    //获取考试的提交的汇总情况
    //@SelectProvider(type=TestProvider.class, method="getSubmitStateResultSQL")
    @Select("SELECT a.id, a.account,a.name ,a.class,b.problem_id,b.accuracy, a.class_id " +
            "FROM (SELECT a.id,a.account,a.name,a.class_id, b.name AS class FROM teach_students a,teach_class b WHERE a.`class_id`=b.`id` AND a.`class_id` IN (SELECT class_id FROM teach_test_class WHERE test_id= #{testId} ) ) a " +
            "JOIN (SELECT user_id,problem_id,MAX(accuracy) AS accuracy FROM teach_submit_code WHERE test_id= #{testId} " +
            "GROUP BY user_id, problem_id ORDER BY problem_id ) b " +
            "ON a.id=b.user_id ")
    public List<Map<String, Object>> getSubmitStateResult(String testId);
    //获取题目与分数
    @Select("SELECT pid, score FROM teach_test_problems WHERE tid=#{testId}")
    public List<Map<String, Object>> getTestProblemInf(String testId);
    //删除指定考试的结果
    @Delete("DELETE FROM teach_test_result WHERE tid = #{testId} ")
    public void deleteTargetTestResult(String testId);
    //保存学生的考试结果
    @InsertProvider(type=TestProvider.class, method="saveTheStudentTestResultSQL")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void saveTheStudentTestResult(@Param("condition")List<Map> params);
   //获取当前正在进行的（实验/考试）
    @Select("select id testId, from_unixtime(end) testEDate from teach_test where unix_timestamp(now()) between start and end ")
    public List<Map> getCurrentTestList();
    //删除编号的IP数据
    @Delete("delete from teach_test_submit where id=#{Id}")
    public void deleteTargetIpData(String Id);
    //************************************************* 相似度
    //获取指定考试的提交数据（代码、学生信息）
    @Select("select  id,problem_id,user_id,submit_code from teach_submit_code where test_id= #{testId} and accuracy=1 order by problem_id, id")
    public List<Map<String, Object>> getSubmitCodeAndStuInf(String testId);
    //保存相似判断结果到数据库
    @InsertProvider(type=TestProvider.class, method="saveTheSimilarityResultSQL")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void saveTheSimilarityResult(@Param("condition")List<Map<String, String>> params);
    //获取指定（实验/考试）相似判断结果
    @Select("select t.f_sid,t.problem_id, a.account fAccount, a.name fName, b.name fClassName, t.s_sid, c.account sAccount,c.name sName,b.name sClassName, t.similarity from teach_similarity_base t,teach_students a,teach_students c,teach_class b,teach_class d where t.`f_userid`=a.id and t.`s_userid`=c.`id` and a.`class_id`=b.id and c.`class_id`=d.`id` and tid= #{testId} ")
    public List<Map> getSimilarityResult(String testId);
    //获取指定的两个提交编号的代码
    @Select("select a.submit_code codeA, b.submit_code codeB from teach_submit_code a, teach_submit_code b where a.id = #{f_sid} and b.id = #{s_sid}")
    public Map getTargetSubmitCode(Map t);
}
