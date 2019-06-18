package com.oj.entity.testing;

/**
 * author zhouli
 * Time 2019年4月1日 10点11分
 * Description 对应数据库中题目实体类
 */
public class Problem {
    //题目id
    private String id;
    //题目名称
    private String name;
    //题目主题
    private String subjectid;
    //题目类型(按选择、主观与ACM划分）：teach_problems表中kind字段关联teach_problems_kind中id字段从而获取到的teach_problems_kind.name字段
    private String kind;
    //是否公开
    private String isPublic;
    //题目描述：teach_problems表中description字段
    private String description;
    //输入样式：teach_problems表中intype字段
    private String intype;
    //输出样式：teach_problems表中outtype字段
    private String outtype;
    //输入样例：teach_problems表中insample字段
    private String insample;
    //输出样例：teach_problems表中outsample字段
    private String outsample;
    //作者：teach_problems表中author字段
    private String author;
    //创建时间：teach_problems表中time字段
    private String time;
    //最大内存：teach_problems表中maxmemory字段
    private String maxmemory;
    //最长时间：teach_problems表中maxtime字段
    private String maxtime;
    //检测输入、输出样例正确与否的代码框供用户输入：teach_problems中的exam_code字段。
    private String exam_code;
   // 展示用例：teach_problems表中test_data_id字段(数据库中该字段全部为0，不显示)。
    private String test_data_id;
    //范例代码：teach_problems表中submit_id字段(数据库中该字段全部为0，不显示)
    private String submit_id;
    //题目星级：teach_problems表中rank字段
    private String rank;
    //is_show_exepl
    private String is_show_exepl;
//    //show_test
//    private String showTest;


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

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntype() {
        return intype;
    }

    public void setIntype(String intype) {
        this.intype = intype;
    }

    public String getOuttype() {
        return outtype;
    }

    public void setOuttype(String outtype) {
        this.outtype = outtype;
    }

    public String getInsample() {
        return insample;
    }

    public void setInsample(String insample) {
        this.insample = insample;
    }

    public String getOutsample() {
        return outsample;
    }

    public void setOutsample(String outsample) {
        this.outsample = outsample;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMaxmemory() {
        return maxmemory;
    }

    public void setMaxmemory(String maxmemory) {
        this.maxmemory = maxmemory;
    }

    public String getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(String maxtime) {
        this.maxtime = maxtime;
    }

    public String getExam_code() {
        return exam_code;
    }

    public void setExam_code(String exam_code) {
        this.exam_code = exam_code;
    }

    public String getTest_data_id() {
        return test_data_id;
    }

    public void setTest_data_id(String test_data_id) {
        this.test_data_id = test_data_id;
    }

    public String getSubmit_id() {
        return submit_id;
    }

    public void setSubmit_id(String submit_id) {
        this.submit_id = submit_id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIs_show_exepl() {
        return is_show_exepl;
    }

    public void setIs_show_exepl(String is_show_exepl) {
        this.is_show_exepl = is_show_exepl;
    }
}