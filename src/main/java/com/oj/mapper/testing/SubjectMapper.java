package com.oj.mapper.testing;
import com.oj.entity.testing.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
@Mapper
public interface SubjectMapper {

    //获取全部主题列表
    @Select("SELECT * FROM teach_subject")
    public List<Map> getSubjectMaplist();


    //插入一条新主题
    @Insert("insert into teach_subject(name,description) values( #{subject_name},#{subject_parent})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void subjectSave(Subject subject);

    //通过id更新主题
    @Update("update teach_subject set name=#{subject_name} where id=#{id}")
    public void subjectUpdate(Subject subject);
    //通过id获取对应的主题信息
    @Select("select * from teach_subject where id = #{id}")
    public List<Map> getSubjectById(@Param("id") String id);
    //主题通过id删除
    @Delete("delete from teach_subject where id=#{id}")
    public void subjectDelete(@Param("id")String id);

    //获取子主题
    @Select("select id from teach_subject where description=#{id}")
    public List<String> getChildSubjectIds(@Param("id")String id);
}
