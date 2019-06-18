package com.oj.mapper.provider.education;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class ClassProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public String getQuerySql(Map<String, Object> params) {

        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        /*SELECT class.id, class.name, major.name as major_name, grade.name as grade_name FROM (teach_class class LEFT JOIN teach_major major ON class.major_id = major.id) LEFT JOIN teach_grade grade ON class.grade_id = grade.id ORDER BY class.id DESC*/

        sql.append(" SELECT class.id, class.name, IFNULL(major.name, ' ') as major_name, class.major_id as major_id, IFNULL(grade.name, ' ') as grade_name, class.grade_id as grade_id FROM (teach_class class LEFT JOIN teach_major major ON class.major_id = major.id) LEFT JOIN teach_grade grade ON class.grade_id = grade.id ");
        sql.append(" WHERE class.id != '0' ");
        if (!StringUtils.isEmpty(info.get("id"))){
            sql.append(" AND class.id = '"+info.get("id")+"' ");
        }
        if (!StringUtils.isEmpty(info.get("name"))){
            sql.append(" AND class.name like '%"+info.get("name")+"%' ");
        }
        if (!StringUtils.isEmpty(info.get("major_id"))){
            sql.append(" AND class.major_id like '%"+info.get("major_id")+"%' ");
        }
        if (!StringUtils.isEmpty(info.get("grade_id"))){
            sql.append(" AND class.grade_id like '%"+info.get("grade_id")+"%' ");
        }
        sql.append(" order by class.id desc");
        System.out.println(sql);
        log.info(sql.toString());
        return sql.toString();
    }
}
/*
*/