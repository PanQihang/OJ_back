package com.oj.mapper.provider.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理provider类
 */
public class TestProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String getTestInfoSql(Map<String, Object> params) {
        Map<String, String> info = (Map<String, String>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("a.id, ");
        sql.append("a.name, ");
        sql.append("'考试' as kind, ");
        sql.append("FROM_UNIXTIME(a.`start`) as start_time, ");
        sql.append("FROM_UNIXTIME(a.`end`) as end_time, ");
        sql.append("admin_id ");
        sql.append("FROM ");
        sql.append("teach_test a ");
        sql.append("INNER JOIN ( ");
        sql.append("SELECT DISTINCT ");
        sql.append("test_id ");
        sql.append("FROM ");
        sql.append("teach_test_class ");
        sql.append("WHERE ");
        sql.append("class_id IN ( ");
        sql.append("SELECT class_id ");
        sql.append("FROM teach_course_class ");
        sql.append("WHERE course_id in ( ");
        sql.append("SELECT course_id ");
        sql.append("FROM teach_admin_course ");
        sql.append("WHERE admin_id = '" + info.get("user_id") + "' ");
        sql.append(") ) ");
        sql.append(") b ON a.id = b.test_id ");
        sql.append("WHERE a.kind = '1' ");
        if (!StringUtils.isEmpty(info.get("testName"))) {
            sql.append("AND a.name like '%" + info.get("testName") + "%' ");
        }
        sql.append("ORDER BY ");
        sql.append("a.id DESC ");

        log.info(sql.toString());
        return sql.toString();
    }

    public String getIpInfoSql(Map<String, Object> params) {
        Map<String, String> info = (Map<String, String>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("select DISTINCT ");
        sql.append("a.id, b.account,b.name,c.name as class,a.sip ,FROM_UNIXTIME(d.`submit_date`) as submit_date ");
        sql.append("from (select id, tid,sid,sip from teach_test_submit where tid=" + info.get("tid") + ")");
        sql.append(" as a,teach_students as b,teach_class as c,teach_submit_code as d ");
        sql.append("where a.sid=d.id and d.user_id=b.id and b.class_id=c.id");
        if (!StringUtils.isEmpty(info.get("account"))) {
            sql.append(" AND b.account = '" + info.get("account") + "' ");
        }
        if (!StringUtils.isEmpty(info.get("name"))) {
            sql.append(" AND b.name = '" + info.get("name") + "' ");
        }
        sql.append(" order by submit_date desc");
        return sql.toString();
    }
//*************************************************************************************************** xln
    /*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */
    //获取指定的考试结果集
    public String getTestScoreResultList(Map<String, Object> params) {
        Map<String, String> info = (Map<String, String>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        //sql.append("SELECT t.account account, t.name name, t.class_name className, t.result testResult, t.all testScore FROM teach_test_result t ");
        sql.append("SELECT t.account account, t.name name, t.class_name className, t.result testResult, t.all testScore FROM "); //最终要获取的属性
        sql.append(" (SELECT * FROM teach_test_result WHERE tid = '"+info.get("testId")+"') t "); //获取指定考试下的所有结果集
        sql.append(" WHERE t.class_id IN (SELECT b.class_id classId FROM "); //获取指定班级
        sql.append(" (SELECT course_id FROM teach_admin_course WHERE admin_id='"+info.get("user_id")+"') a, "); //获取指定课程
        sql.append(" teach_course_class b "); //
        sql.append(" WHERE a.course_id = b.course_id "); //
        if (!StringUtils.isEmpty(info.get("majorId")))
            sql.append(" AND  b.class_id IN (SELECT id FROM teach_class WHERE major_id='"+info.get("majorId")+"')"); //获取指定专业下的课程
        if(!StringUtils.isEmpty(info.get("classId")))
            sql.append(" AND t.class_id ='"+info.get("classId")+"' ");
        sql.append(") ");
        sql.append("ORDER BY ");
        sql.append("t.all DESC, t.account  "); //在成绩降序的基础上，学号增序
        log.info(sql.toString());
        return sql.toString();
    }
    public String getcompScoreResultList(Map<String, Object> params) {
        Map<String, String> info = (Map<String, String>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        //sql.append("SELECT t.account account, t.name name, t.class_name className, t.result testResult, t.all testScore FROM teach_test_result t ");
        sql.append("SELECT t.account account, t.name name, t.class_name className, t.result testResult, t.all testScore FROM\n" +
                "teach_test_result t  where ");
        sql.append("tid='"+info.get("testId")+"'");
        sql.append("ORDER BY ");
        sql.append("t.all DESC, t.account  "); //在成绩降序的基础上，学号增序
        log.info(sql.toString());
        return sql.toString();
    }
    //获取指定考试的成绩统计情况
    public String getStatisticalResultSQL(Map<String, Object> params) {
        Map<String, String> info = (Map<String, String>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(t.all) 'value', t.all 'name' FROM teach_test_result t WHERE t.tid = "+info.get("testId")+" ");
        if(!StringUtils.isEmpty(info.get("classId")))
            sql.append(" AND t.class_id = "+info.get("classId"));
        sql.append(" GROUP BY t.all ORDER BY t.all DESC");

        log.info(sql.toString());
        return sql.toString();
    }
    //获取考试的提交的汇总情况
    public String getSubmitStateResultSQL(Map<String, Object> params){
        Map<String, String> info = (Map<String, String>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.account,a.name ,a.class,b.problem_id,b.accuracy, a.class_id " +
                " FROM (SELECT a.id,a.account,a.name,a.class_id, b.name AS class FROM teach_students a,teach_class b WHERE a.`class_id`=b.`id` AND a.`class_id` IN (SELECT class_id FROM teach_test_class WHERE test_id="+info.get("testId")+") ) a " +
                " JOIN (SELECT user_id,problem_id,MAX(accuracy) AS accuracy FROM teach_submit_code WHERE test_id="+info.get("testId")+" " +
                " GROUP BY user_id, problem_id ORDER BY problem_id ) b " +
                " ON a.id=b.user_id    ");
        log.info(sql.toString());
        return sql.toString();
    }
    //保存学生考试结果
    public String saveTheStudentTestResultSQL(Map<String, Object> params){

        List<Map<String, String>> temp = (List<Map<String, String>>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO teach_test_result  VALUES  " );
        for(int i=0; i<temp.size(); i++) {
            Map<String, String> info = temp.get(i);
            sql.append((0==i?"":",")+"(NULL,");
            sql.append(info.get("testId") + ", ");
            sql.append("\"" + info.get("first_ip") + "\", ");
            sql.append(info.get("sid") + ", ");
            sql.append("\"" + info.get("name") + "\", ");
            sql.append("\"" + info.get("account") + "\", ");
            sql.append(info.get("class_id") + ", ");
            sql.append("\"" + info.get("class") + "\", ");
            sql.append("\"" + info.get("result") + "\", ");
            sql.append(info.get("all") + ") ");
        }
        log.info(sql.toString());
        return sql.toString();
    }
    //保存学生考试相似判断结果
    public String saveTheSimilarityResultSQL(Map<String, Object> params){
        List<Map<String, String>> temp = (List<Map<String, String>>) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO teach_similarity_base VALUES ");
        for(int i=0; i<temp.size(); i++){
            Map<String, String> info = temp.get(i);
            sql.append((0==i?"":",")+"(NULL, ");
            sql.append(info.get("tid")+", ");
            sql.append(info.get("f_sid")+", ");
            sql.append(info.get("f_userid")+", ");
            sql.append(info.get("s_sid")+", ");
            sql.append(info.get("s_userid")+", ");
            sql.append(info.get("similarity")+", ");
            sql.append(info.get("problem_id")+") ");
        }
        log.info(sql.toString());
        return sql.toString();
    }
}
