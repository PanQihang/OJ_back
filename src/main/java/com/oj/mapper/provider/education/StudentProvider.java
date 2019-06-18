package com.oj.mapper.provider.education;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class StudentProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String getQuerySql(Map<String, Object> params){
        Map<String, String> inf = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT stu.id, stu.`account`, stu.`name` AS student_name , cla.`name` AS class_name, cla.`id` AS class_id FROM teach_students stu , teach_class cla WHERE stu.class_id = cla.id ");

        if(!StringUtils.isEmpty(inf.get("id")))
            sql.append(" AND stu.id = "+inf.get("id"));
        if(!StringUtils.isEmpty(inf.get("account")))
            sql.append(" AND stu.account LIKE '%"+inf.get("account")+"%' ");
        if(!StringUtils.isEmpty(inf.get("student_name")))
            sql.append(" AND stu.name LIKE '%"+inf.get("student_name")+"%' ");
        if(!StringUtils.isEmpty(inf.get("class_id")))
            sql.append(" AND cla.id = '"+inf.get("class_id")+"' ");
        sql.append(" ORDER by stu.account DESC");
        log.info(sql.toString());
        return sql.toString();

        //AND stu.account LIKE '%%'")
    }



}
