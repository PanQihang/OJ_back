package com.oj.mapper.exam;

import org.apache.ibatis.annotations.*;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Map;

//注册Mapper
@Mapper
public interface SummaryMapper {
    //教师查询所有报表
    @Select("select * from teach_report where user_id = #{id}")
    public List<Map> getSummary(@Param("id") String id);

    //admin查询所有报表
    @Select("select * from teach_report order by time desc")
    public List<Map> getAllSummary();

    //通过id删除报表
    @Delete("delete from teach_report where id=#{id}")
    public void deleteSummary(String id);
    //连带删除report_test表中数据
    @Delete("delete from teach_report_test where report_id = #{id}")
    public void deleteReporttest(String id);
    //获取考试列表
    @Select("SELECT a.id, a.name, '考试' as kind, FROM_UNIXTIME(a.`start`) as start_time, FROM_UNIXTIME(a.`end`) " +
            "as end_time, admin_id FROM teach_test a INNER JOIN ( SELECT DISTINCT test_id FROM teach_test_class " +
            "WHERE class_id IN ( SELECT class_id FROM teach_course_class WHERE course_id in ( SELECT course_id FROM " +
            "teach_admin_course WHERE admin_id = #{id} ) ) ) b ON a.id = b.test_id WHERE a.kind = '1' ORDER BY a.id DESC")
    public List<Map> getTest(String id);

    //新增报表
    @Insert("insert into teach_report(name, time, user_id) values(#{name}, #{time}, #{id}) ")
    public void addSummary(@Param("name")String name, @Param("time")String time, @Param("id") String id);

    //查询新增id
    //@Select("select id from teach_report where name = #{name}")
    @Select("SELECT LAST_INSERT_ID();")
    public String selectId();

    //新增report_test表
    @Insert("insert into teach_report_test(report_id, test_id) values(#{report_id},#{test_id})")
    public void addReport_test(@Param("report_id") String report_id, @Param("test_id") String test_id);

    //获取考试列表
    @Select("SELECT t.id,t.name,t.start,t.end FROM teach_test t, teach_report_test k WHERE k.report_id=#{id} AND k.test_id=t.id")
    public List<Map> getTestList(String id);

    //获取考试结果信息
    @Select("SELECT\n" +
            "\tallUser.id ,\n" +
            "\tallUser.account,\n" +
            "\tallUser.name,\n" +
            "\tallUser.class_name,\n" +
            "\tselectedUser.result,\n" +
            "\tselectedUser.all\n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tts.* ,(SELECT NAME FROM teach_class WHERE id = ts.class_id) AS class_name\n" +
            "\tFROM\n" +
            "\t\tteach_students ts\n" +
            "\t\tINNER JOIN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tDISTINCT a.class_id\n" +
            "\t\tFROM\n" +
            "\t\t\tteach_test_class a\n" +
            "\t\t\tINNER JOIN ( SELECT test_id FROM teach_report_test WHERE report_id = #{id} ) b ON a.test_id = b.test_id \n" +
            "\t\t) cxbj ON ts.class_id = cxbj.class_id \n" +
            "\t) allUser\n" +
            "\tLEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\t ttr.tid,\n" +
            "\t\t ttr.first_ip,\n" +
            "\t\t ttr.sid,\n" +
            "\t\t ttr.name,\n" +
            "\t\t ttr.account,\n" +
            "\t\t ttr.class_id,\n" +
            "\t\t ttr.class_name,\n" +
            "\t\t ttr.result,\n" +
            "\t\t ttr.all\n" +
            "\tFROM\n" +
            "\t\tteach_test_result ttr\n" +
            "\tINNER JOIN ( SELECT * FROM teach_report_test WHERE report_id = #{id} ) trt ON ttr.tid = trt.test_id \n" +
            "\t) selectedUser ON allUser.account = selectedUser.account")
    public List<Map> getTestScoreResult(@Param("id") String id);

    //统计考试成绩分布
    @Select("SELECT COUNT(t.all) 'value', t.all 'name' FROM teach_test_result t WHERE t.tid IN " +
            "(SELECT test_id FROM teach_report_test WHERE report_id = #{id}) GROUP BY t.all ORDER BY t.all DESC")
    public List<Map> getTheStatisticalResult(String id);

    //获取报表名称
    @Select("select name from teach_report where id = #{id}")
    public String getSummaryName(String id);
}
