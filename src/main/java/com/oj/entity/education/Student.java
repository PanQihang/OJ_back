package com.oj.entity.education;
/**
 * @author xielanning
 * @Time 2019年4月1日 15点21分
 * @Description 对应数据库中学生表实体类
 */
public class Student {

    private String id;
    private String account;
    private String password;
    private String name;
    private String class_id;


    public Student(String account, String password, String name, String class_id) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.class_id = class_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", class_id='" + class_id + '\'' +
                '}';
    }
}
