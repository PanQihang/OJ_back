package com.oj.mapper.provider.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyFileProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public String getQuerySql(Map<String, Object> params) {

        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        /*SELECT class.id, class.name, major.name as major_name, grade.name as grade_name FROM (teach_class class LEFT JOIN teach_major major ON class.major_id = major.id) LEFT JOIN teach_grade grade ON class.grade_id = grade.id ORDER BY class.id DESC*/

        sql.append(" SELECT myfile.id as id, myfile.name as name, admin.name as uploader_name, myfile.upload_time as upload_time, myfile.size as size, myfile.flag as flag FROM teach_myfile myfile, teach_admin admin ");
        sql.append(" WHERE myfile.id != '0' ");
        if (!StringUtils.isEmpty(info.get("name"))){
            sql.append(" AND myfile.name like '%"+info.get("name")+"%' ");
        }
        if (!StringUtils.isEmpty(info.get("uploader_id"))){
            sql.append(" AND myfile.uploader_id = "+info.get("uploader_id")+" ");
        }
        String id = info.get("id");
        //System.out.println("id : "+id);
        if(!id.equals("2"))sql.append(" AND uploader_id = "+id+" ");
        sql.append(" AND myfile.uploader_id = admin.id order by myfile.upload_time desc");
        System.out.println(sql);
        System.out.println("params : " + params.toString());
        log.info(sql.toString());
        return sql.toString();
    }
}
/*
*/