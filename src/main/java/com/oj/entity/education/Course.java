package com.oj.entity.education;
/**
 * @author zt
 * @Time 2019年4月1日 12点59分
 * @Description 对应数据库中课程表实体类
 */
public class Course {
    //课程ID
    private String id;
    //课程名称
    private String name;

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(String Course_id)
    {
        this.id = Course_id;
    }
    public void setName(String Course_name)
    {
        this.name = Course_name;
    }

}
