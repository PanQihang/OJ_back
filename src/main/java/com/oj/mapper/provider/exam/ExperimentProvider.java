package com.oj.mapper.provider.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 实验管理provider类
 */
public class ExperimentProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getExperInfoSql(Map<String, Object> params){
        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("a.id, ");
        sql.append("a.name, ");
        sql.append("'实验' as kind, ");
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
        sql.append("WHERE admin_id = '"+info.get("user_id")+"' ");
        sql.append(") ) ");
        sql.append(") b ON a.id = b.test_id ");
        sql.append("WHERE a.kind = '2' ");
        if (!StringUtils.isEmpty(info.get("experName"))){
            sql.append("AND a.name like '%"+info.get("experName")+"%' ");
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
        sql.append("b.account,b.name,c.name as class,a.sip ,FROM_UNIXTIME(d.`submit_date`) as submit_date ");
        sql.append("from (select tid,sid,sip from teach_test_submit where tid="+info.get("tid")+")");
        sql.append(" as a,teach_students as b,teach_class as c,teach_submit_code as d ");
        sql.append("where a.sid=d.id and d.user_id=b.id and b.class_id=c.id");
        if (!StringUtils.isEmpty(info.get("account"))){
            sql.append(" AND b.account = '"+info.get("account")+"' ");
        }
        if (!StringUtils.isEmpty(info.get("name"))){
            sql.append(" AND b.name = '"+info.get("name")+"' ");
        }
        sql.append(" order by submit_date desc");
        return sql.toString();
    }
}
