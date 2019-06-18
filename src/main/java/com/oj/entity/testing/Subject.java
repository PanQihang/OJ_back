package com.oj.entity.testing;
/**
 * @author liyue
 * @Time 2019年3月27日 13点00分
 * @Description 对应数据库中主题表实体类
 */
public class Subject {
    //主题id
    private String id;
    //主题名称
    private String subject_name;
    //父节id
    private String subject_parent;
    public String getId(){
        return id;
    }
    public void  setId(String id){
        this.id=id;
    }
    public String getSubject_name(){
        return subject_name;
    }
    public void setSubject_name(String subject_name){
        this.subject_name=subject_name;
    }
    public String getSubject_parent(){
        return subject_parent;
    }
    public void setSubject_parent(String subject_parent){
        this.subject_parent= subject_parent;
    }
}
