package com.oj.mapper.provider.education;

import com.oj.entity.education.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by AC on 2019/4/2 14:59
 */
public class NoticeProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String getQuerySql(Map<String, Object> params){
        Notice notice = (Notice)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ( @i := @i + 1 ) AS num, t1.* ");
        sql.append(" FROM ( SELECT * FROM teach_notice WHERE id != '32' ");
        if (!StringUtils.isEmpty(notice.getTitle())){
            sql.append(" AND title like '%"+notice.getTitle()+"%' ");
        }
        if (!StringUtils.isEmpty(notice.getAuthor())){
            sql.append(" AND author like '%"+notice.getAuthor()+"%' ");
        }
        if (!StringUtils.isEmpty(notice.getTime())){
            int t = Integer.parseInt(notice.getTime())+86400;
            sql.append(" AND time > '"+notice.getTime()+"' and time < '"+t+"'");
        }
        sql.append(" ) t1, ( SELECT @i := 0 ) t2 ");
        sql.append(" order by time desc");
        log.info(sql.toString());
        return sql.toString();
    }
}
