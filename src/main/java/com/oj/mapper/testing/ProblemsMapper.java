package com.oj.mapper.testing;

import com.oj.entity.testing.Problem;
import com.oj.mapper.provider.testing.ProblemsProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProblemsMapper {


    //通过ProblemsProvider类中的getQuerySql()方法动态构建查询语句
    @SelectProvider(type= ProblemsProvider.class, method = "getQuerySql")
    //查询用户结果，返回Map类型List
     List<Map> getProblemsMapList(@Param("condition")Map<String, String> param);


    //通过id获取题目详细信息
    @Select("SELECT  p.*,COUNT(sc.submit_state = 1 OR NULL) AC_number,COUNT(sc.id) submit_number,s.name subject\n" +
            "FROM teach_problems p LEFT JOIN teach_subject s ON s.id = p.`subjectid`\n" +
            "left join teach_submit_code sc on  p.id = sc.`problem_id`  WHERE p.id =  #{id}\n" +
            "GROUP BY p.id")
     List<Map> getProblemById(@Param("id")String id);


    //更新题目
    @Update("update teach_problems set  name=#{name},subjectid=#{subjectid},public=#{isPublic}, description=#{description}, intype=#{intype}, " +
           "outtype=#{outtype}, insample=#{insample}, outsample=#{outsample},author=#{author}, maxtime=#{maxtime},maxmemory=#{maxmemory}," +
            "rank=#{rank},exam_code=#{exam_code},is_show_exepl=#{is_show_exepl} where id = #{id}")
     int update(Problem problem);
    //删除题目
    @Delete("delete from teach_problems where id = #{id}")
     void problemDelete(String id);

    //增加题目
    @Insert("insert into teach_problems (name,description,kind,intype,outtype,insample,outsample,author,time,subjectid,maxtime," +
            "maxmemory,public,rank,test_data_id,submit_id,exam_code,is_show_exepl) " +
            "values (#{name},#{description},#{kind},#{intype},#{outtype},#{insample},#{outsample},#{author}," +
            "#{time},#{subjectid},#{maxtime},#{maxmemory},#{isPublic},#{rank},#{test_data_id},#{submit_id},#{exam_code},#{is_show_exepl})" )
     int insert(Problem problem);


    //单道题目完成情况分析
    //提交数量
    @Select("SELECT COUNT(*) FROM teach_submit_code WHERE problem_id = #{id}")
     int submitCount(String id);
    //正确通过数量
    @Select("SELECT COUNT(*) FROM teach_submit_code WHERE problem_id = #{id} AND submit_state = 1")
      int successCount(String id);
    //编译错误
    @Select("SELECT COUNT(*) FROM teach_submit_code WHERE problem_id = #{id} AND submit_state = 8")
    int compileErrorCount(String id);
    //格式错误
    @Select("SELECT COUNT(*) FROM teach_submit_code WHERE problem_id = #{id} AND submit_state = 2")
      int formatErrorCount(String id);
    //答案错误
    @Select("SELECT COUNT(*) FROM teach_submit_code WHERE problem_id = #{id} AND submit_state = 3")
      int wrongAnswerCount(String id);
    //一条语句返回
    @Select("SELECT COUNT(*) submitCount,COUNT(submit_state = 1 OR NULL) successCount," +
            "COUNT(submit_state = 2 OR NULL) formatErrorCount," +
            "COUNT(submit_state = 3 OR NULL) wrongAnswerCount," +
            "COUNT(submit_state = 8 OR NULL) compileErrorCount " +
            "FROM teach_submit_code WHERE problem_id = #{id}")
     List<Map> getProblemAnalyze(String id);

    //单道题目测试案例通过情况


}
