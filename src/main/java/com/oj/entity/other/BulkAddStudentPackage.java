package com.oj.entity.other;

import java.util.List;

public class BulkAddStudentPackage {
    private String classId;
    private String Num;
    private List<NewStu> data;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public List<NewStu> getData() {
        return data;
    }

    public void setData(List<NewStu> data) {
        this.data = data;
    }
}
