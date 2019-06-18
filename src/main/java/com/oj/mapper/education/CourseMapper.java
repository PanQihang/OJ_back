package com.oj.mapper.education;

import com.oj.entity.education.Course;
import com.oj.entity.system.User;
import com.oj.mapper.provider.education.CourseProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseMapper {

    //通过id获取对应的课程信息
    @Select("select * from teach_course where id = #{id}")
    public List<Map> getCourseById(@Param("id") String id);

    //通过name获取对应的课程信息
    @Select("select * from teach_course where name = #{name}")
    public List<Map> getCourseByName(@Param("name") String name);

    //通过CourseProvider类中的getQuerySql()方法动态构建查询语句
    @SelectProvider(type= CourseProvider.class, method = "getQuerySql")
    //查询课程结果，返回Map类型List
    public List<Map> getCourseMapList(@Param("condition")Map<String, String> param);

    //保存课程
    @Insert("insert into teach_course (name) values(#{name})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public int save(Course course);

    //更新课程
    @Update("update teach_course set name=#{name} where id = #{id}")
    public int update(Course course);

    //通过课程id删除课程关联班级信息
    @Delete("delete from teach_course_class where course_id = #{course_id}")
    public void courseClassDelete(@Param("course_id") String course_id);

    //删除课程
    @Delete("delete from teach_course where id = #{id}")
    public void courseDelete(String id);

    //获取班级列表
    //@Select("select b.id as class_id, b.name as class_name from teach_course a, teach_class b where b.id = ( select b.id from teach_course_class c where c.course_id = a.id and c.class_id = b.id ) order by b.id;")
    @Select("SELECT * FROM teach_class a LEFT JOIN (SELECT course_id, class_id FROM teach_course_class WHERE course_id =#{id}) b ON a.id = b.class_id ORDER BY a.id DESC")
    public List<Map> getClassMapByCourseList(String id);

    //保存课程班级表
    @Insert("insert into teach_course_class (course_id, class_id) values(#{course_id}, #{class_id})")
    public void saveCourseClassList(@Param("course_id") String course_id, @Param("class_id") String class_id);
}
