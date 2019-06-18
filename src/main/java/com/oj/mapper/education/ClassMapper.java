package com.oj.mapper.education;

import com.oj.entity.education.Class;
import com.oj.mapper.provider.education.ClassProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassMapper {
    //通过ClassProvider类中的getQuerySql()方法动态构建查询语句
    @SelectProvider(type= ClassProvider.class, method = "getQuerySql")
    //查询课程结果，返回Map类型List
    public List<Map> getClassMapList(@Param("condition")Map<String, String> param);


    //获取学院下拉信息
    @Select("SELECT id, name FROM teach_major ")
    public List<Map> getMajorSelectInfo();

    //获取年级下拉信息
    @Select("SELECT id, name FROM teach_grade order by name desc")
    public List<Map> getGradeSelectInfo();
    //通过id获取对应的班级信息
    @Select("select * from teach_class where id = #{id}")
    public List<Map> getClassById(@Param("id") String id);

    //通过name获取对应的班级信息
    @Select("select * from teach_class where name = #{name}")
    public List<Map> getClassByName(@Param("name") String name);

    //保存班级
    @Insert("insert into teach_class (name, major_id, grade_id, class) values(#{name}, #{major_id}, #{grade_id}, #{class_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public int save(Class clas);

    //更新班级
    @Update("update teach_class set name=#{name}, major_id=#{major_id}, grade_id=#{grade_id} where id = #{id}")
    public int update(Class clas);

    //删除班级
    @Delete("delete from teach_class where id = #{id}")
    public void classDelete(String id);

    //获取学生列表
    @Select("select students.account as student_account, students.name as student_name from teach_students students, teach_class class where students.class_id = class.id and class.id = #{id} order by students.account;")
    public List<Map> getStudentMapByClassList(String id);
}
