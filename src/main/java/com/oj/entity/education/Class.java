package com.oj.entity.education;
/**
 * @author zt
 * @Time 2019年4月2日 17点24分
 * @Description 对应数据库中班级表实体类
 */
public class Class {
    //班级ID
    private String id;
    //班级名称
    private String name;
    //学院ID
    private String major_id;
    //年级ID
    private String grade_id;
    //班级号ID
    private String class_id;

    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getMajor_id()
    {
        return major_id;
    }
    public String getGrade_id()
    {
        return grade_id;
    }
    public String getClass_id()
    {
        return class_id;
    }

    public void setId(String Class_id)
    {
        this.id = Class_id;
    }
    public void setName(String Class_name)
    {
        this.name = Class_name;
    }
    public void setMajor_id(String major_id)
    {
        this.major_id = major_id;
    }
    public void setGrade_id(String grade_id)
    {
        this.grade_id = grade_id;
    }
    public void setClass_id(String class_id)
    {
        this.class_id = class_id;
    }
}
