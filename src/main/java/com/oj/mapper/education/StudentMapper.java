package com.oj.mapper.education;
import com.oj.entity.education.Student;
import com.oj.entity.system.Auth;
import com.oj.mapper.provider.education.StudentProvider;
import com.oj.mapper.provider.system.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 学生数据表操作接口
 */

@Mapper
public interface StudentMapper {

    //查找学生
    ////通过姓名、学号、班级模糊查询符号的学生集
    @SelectProvider(type= StudentProvider.class, method = "getQuerySql")
    public List<Map> getTargetStudent(@Param("condition")Map<String, String> param);
    ////通过班级id查找学生集
    @Select("SELECT * FROM teach_students where class_id = #{classId} ORDER BY id DESC")
    public List<Map> getStudentListByClassId(String classId);
    ////通过学生姓名查找学生集
    @Select("SELECT * FROM teach_students where name = #{name} ORDER BY id DESC")
    public List<Map> getStudentListByName(String name);
    ////获取所有学生
    @Select("SELECT * FROM teach_students ORDER BY id DESC")
    public List<Map> getStudentList();
    ////通过学生编号查找学生
    @Select("SELECT * FROM teach_students where id = #{id}")
    public Map getTheStudentById(String id);
    ////通过学生学号查找学生
    @Select("SELECT id, name, account, password, class_id FROM teach_students where account = #{account}")
    public Map getTheStudentByAccount(String account);

    //查找班级
    @Select("SELECT id, name FROM teach_class ")
    public List<Map> getClassList();

    //增加学生
    @Insert("INSERT INTO teach_students(account, password, name, class_id) values(#{account}, #{password}, #{name}, #{class_id})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    //public int addNewStudent(@Param("account")String account, @Param("name")String name, @Param("classId")String classId);
   // @Insert("INSERT INTO teach_students values(#{account}, #{password}, #{name}, #{classId})")
    public int addNewStudent(Student student);

    //删除学生
    ////通过编号删除学生
    @Delete("DELETE FROM teach_students where id = #{id}")
    public void deleteTheStudentById(String id);
    ////通过学号删除学生
    @Delete("DELETE FROM teach_students where account = #{account}")
    public void deleteTheStudentByAccount(String account);
    ////通过班级id删除学生集
    @Delete("DELETE FROM teach_students where class_id = #{class_id}")
    public void deleteStudentByClassId(String class_id);

    //修改学生
    ////通过编号修改学生信息
    @Update("UPDATE teach_students set account = #{account}, name = #{name}, class_id = #{class_id} where id = #{id}")
    public int updateTheStudent(Student student);
    ////通过编号修改学生密码
    @Update("UPDATE teach_students set password = #{password} where id = #{id}")
    public void updateTheStudentPWById( @Param(value = "id") String id, @Param(value = "password") String password);

    //查找班级
    @Select("SELECT id FROM teach_class where name = #{className}")
    public Map getTheClassIdByName(@Param(value = "className") String className);



}
