package com.oj.entity.testing;

/**
 * @author zhouli
 * @Description 保存后台的测试代码
 */
public class Case {
    private String id;
    private String problem_id;
    private String in_put;
    private String out_put;
    private String time;
    private String description;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public String getIn_put() {
        return in_put;
    }

    public void setIn_put(String in_put) {
        this.in_put = in_put;
    }

    public String getOut_put() {
        return out_put;
    }

    public void setOut_put(String out_put) {
        this.out_put = out_put;
    }
}
