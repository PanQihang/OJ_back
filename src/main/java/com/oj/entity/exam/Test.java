package com.oj.entity.exam;

public class Test {

    //实验主键
    private String id;
    //实验名称
    private String name;
    //实验开始时间
    private String start;
    //实验结束时间
    private  String end;
    //实验描述
    private String description;
    //实验种类
    private String kind;
    //禁止多机登录
    private String isIp;
    //限制允许登录机房
    private String onlyIp;
    //是否创建了报表，添加时为0
    private String report;
    //创建人ID
    private String adminId;

//SELECT a.id, a.name, '实验' as kind, FROM_UNIXTIME(a.`start`) as start_time, FROM_UNIXTIME(a.`end`) as end_time FROM teach_test a INNER JOIN ( SELECT DISTINCT test_id FROM teach_test_class WHERE class_id IN ( SELECT class_id FROM teach_course_class WHERE course_id in ( SELECT course_id FROM teach_admin_course WHERE admin_id = '2' ) ) ) b ON a.id = b.test_id WHERE a.kind = '2' ORDER BY a.id DESC


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIsIp() {
        return isIp;
    }

    public void setIsIp(String isIp) {
        this.isIp = isIp;
    }

    public String getOnlyIp() {
        return onlyIp;
    }

    public void setOnlyIp(String onlyIp) {
        this.onlyIp = onlyIp;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", description='" + description + '\'' +
                ", kind='" + kind + '\'' +
                ", isIp='" + isIp + '\'' +
                ", onlyIp='" + onlyIp + '\'' +
                ", report='" + report + '\'' +
                ", adminId='" + adminId + '\'' +
                '}';
    }
}
