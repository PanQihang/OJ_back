package com.oj.mapper.system;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by panqihang on 2019/4/12 17:03
 */
@Mapper
public interface HomepageMapper {
    //获取所有待办项
//    @Select("SELECT a.id,a.NAME,FROM_UNIXTIME( a.`start` ) AS start_time,FROM_UNIXTIME( a.`end` ) AS end_time \n" +
//            "FROM teach_test a INNER JOIN (SELECT DISTINCT test_id FROM teach_test_class WHERE class_id IN (\n" +
//            "SELECT class_id FROM teach_course_class WHERE course_id IN ( SELECT course_id FROM teach_admin_course WHERE admin_id = #{id} ) ) ) b \n" +
//            "ON a.id = b.test_id ORDER BY a.id DESC")
    @Select("SELECT a.kind,a.id,a.NAME, a.`start`  AS start_time, a.`end`  AS end_time \n" +
            "FROM teach_test a INNER JOIN (SELECT DISTINCT test_id FROM teach_test_class WHERE class_id IN (\n" +
            "SELECT class_id FROM teach_course_class WHERE course_id IN ( SELECT course_id FROM teach_admin_course WHERE admin_id = #{id} ) ) ) b \n" +
            "ON a.id = b.test_id ORDER BY a.id DESC")
    public List<Map> getpending(String id);

    //获取总提交提交
    @Select("SELECT COUNT(submit_date) FROM teach_submit_code WHERE submit_date between #{starttime} and #{endtime}")
    public String getsubmit(@Param("starttime") String starttime, @Param("endtime")String endtime);

    //获取AC总数
    @Select("SELECT COUNT(submit_date) FROM teach_submit_code WHERE (submit_date between #{starttime} and #{endtime}) AND (submit_state=1 OR submit_state=2)")
    public String getac(@Param("starttime")String starttime,@Param("endtime")String endtime);

    //清空本月提交统计数据表
    @Delete("truncate table teach_month_submit")
    public void clearmonth();

    //存入统计数据
    @Insert("insert into teach_month_submit(date,AC,number) values(#{date}, #{ac}, #{number})")
    public int savemonth(@Param("date")String date,@Param("ac")String ac, @Param("number")String number);

    //从teach_month_submit查询提交数据
    @Select("select * from teach_month_submit order by id desc")
    public List<Map> getmonthsubmit();
}
