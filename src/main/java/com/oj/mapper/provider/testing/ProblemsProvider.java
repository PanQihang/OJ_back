package com.oj.mapper.provider.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhouli
 * Time 2019年4月2日 11点15分
 * Description 与Problems表相关动态sql生成
 */
public class ProblemsProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getQuerySql(Map<String, Object> params){
        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ( @i := @i + 1 ) AS num,t1.* ");
        sql.append(" FROM( select p.id id,p.name name,IF(p.public = 'on','是','否') public,AC_number,submit_number, concat(convert((AC_number * 100 / submit_number),decimal(18, 2)),'%') AC_rate,s.name subject");
        sql.append(" FROM teach_problems p");
        sql.append(" LEFT JOIN (SELECT  p.`id` id,COUNT(sc.submit_state = 1 OR NULL) AC_number,COUNT(sc.id) submit_number");
        sql.append(" FROM teach_submit_code sc");
        sql.append(" LEFT JOIN teach_problems p ON p.id = sc.problem_id");
        sql.append(" GROUP BY p.id) AS a");
        sql.append(" on p.`id` = a.id");
        sql.append(" LEFT JOIN teach_subject s ON p.subjectid = s.id");
        sql.append(" WHERE p.id > 0");

        if (!StringUtils.isEmpty(info.get("id"))){
            sql.append(" AND p.id = '"+info.get("id")+"' ");
        }
        if (!StringUtils.isEmpty(info.get("name"))){
            sql.append(" AND p.name like '%"+info.get("name")+"%' ");
        }
        if (!StringUtils.isEmpty(info.get("subject"))){
            sql.append(" AND p.subjectid = "+info.get("subject"));
        }
        sql.append(" order by p.id desc");

        sql.append(" ) t1,( SELECT @i := 0 ) t2 ");
        log.info(sql.toString());
        return sql.toString();
    }
}
