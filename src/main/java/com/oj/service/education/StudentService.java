package com.oj.service.education;

import com.oj.entity.education.Student;
import com.oj.entity.other.BulkAddStudentPackage;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 学生用户管理相关功能Service接口
 */
public interface StudentService {

    //##查找学生
    ////通过姓名、学号、班级模糊查询符号的学生集
    public List<Map> getTargetStudentList(Map<String, String> param);
    //通过学生编号获取学生
    public Map getTheStudentById(String id);

    //##查找班级
    ////获取所有班级
    public List<Map> getClassList();

    //##新增学生
    public int addNewStudent(Student student) throws Exception;
    //##批量添加学生
    @Transactional(rollbackFor=Exception.class)
    public int addMoreNewStudent(BulkAddStudentPackage basp) throws Exception;

    //##删除学生
    //通过学生编号删除学生
    public void deleteTheStudentById(String id) ;
    //删除指定班级编号的学生集
    public void deleteStudentByClassId(String classId) ;

    //##修改学生
    //通过学生编号修改学生信息
    public int updateTheStudentById(String id, Student student) throws Exception;
    //通过学生编号修改学生密码
    public void updateTheStudentPWById(String id, String pw) ;


    //通过班级名称获取班级id-------warning
    public String getTheClassIdByName(String className);
    //public List getBankListByExcel(InputStream in, String fileName) throws Exception;



}
