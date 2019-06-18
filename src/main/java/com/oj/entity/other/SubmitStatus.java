package com.oj.entity.other;

public class SubmitStatus {
    //题目id
    private String problem_id;
    //主题名称
    private String subject_name;
    //父节id
    private String subject_parent;
    public String getId(){
        return problem_id;
    }
    public void  setId(String id){
        this.problem_id=problem_id;
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
