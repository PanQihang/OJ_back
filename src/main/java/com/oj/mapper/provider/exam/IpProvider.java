package com.oj.mapper.provider.exam;

import com.oj.entity.exam.Ip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by AC on 2019/4/1 16:22
 */
public class IpProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 生成角色信息查询的sql语句
     * @param params
     * @return 查询sql
     */
    public String getQuerySql(Map<String, Object> params){
        Ip ip = (Ip)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ( @i := @i + 1 ) AS num, t1.* ");
        sql.append(" FROM ( SELECT * FROM teach_ip WHERE id != '32' ");
        if (!StringUtils.isEmpty(ip.getIp())){
            sql.append(" AND ip like '%"+ip.getIp()+"%' ");
        }
        sql.append(" ) t1, ( SELECT @i := 0 ) t2 ");
        sql.append(" order by id desc");
        log.info(sql.toString());
        return sql.toString();
    }
}
