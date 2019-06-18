package com.oj.mapper.provider.competition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class CompetitionProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getcompetitionInfoSql(Map<String, Object> params){
        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("id, ");
        sql.append("name, ");
        sql.append("'竞赛' as kind ,");
        sql.append("FROM_UNIXTIME(start) as start_time, ");
        sql.append("FROM_UNIXTIME(end) as end_time ");
        sql.append("FROM ");
        sql.append("teach_test ");
        sql.append("WHERE kind = '4' ");
        if (!StringUtils.isEmpty(info.get("experName"))){
            sql.append("AND name like '%"+info.get("experName")+"%' ");
        }
        sql.append("ORDER BY ");
        sql.append("id DESC ");

        log.info(sql.toString());
        return sql.toString();
    }
}
