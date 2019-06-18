package com.oj.mapper.provider.education;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class CourseProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getQuerySql(Map<String, Object> params) {

        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        //sql.append(" SELECT ( @i := @i + 1 ) AS id, name ");
        sql.append(" SELECT id, name ");
        sql.append(" FROM teach_course ");
        sql.append(" WHERE id != '0' ");
        //sql.append(" AND b.id = (SELECT b.id FROM teach_course_class c WHERE c.course_id = a.id AND b.id = c.class_id) ");
        if (!StringUtils.isEmpty(info.get("id"))){
            sql.append(" AND id = '"+info.get("id")+"' ");
        }
        if (!StringUtils.isEmpty(info.get("name"))){
            sql.append(" AND name like '%"+info.get("name")+"%' ");
        }
        sql.append(" order by id desc");
        System.out.println(sql);
        log.info(sql.toString());
        return sql.toString();
    }
}
