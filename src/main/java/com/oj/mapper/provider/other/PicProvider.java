package com.oj.mapper.provider.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
/**
 * @author zt
 * @Time 2019年5月16日 13点24分
 * @Description 图片轮播sql拼接
 */
public class PicProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String getQuerySql(Map<String, Object> params) {

        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        /*SELECT class.id, class.name, major.name as major_name, grade.name as grade_name FROM (teach_class class LEFT JOIN teach_major major ON class.major_id = major.id) LEFT JOIN teach_grade grade ON class.grade_id = grade.id ORDER BY class.id DESC*/

        sql.append(" SELECT pic.id as id, pic.name as name, admin.name as uploader_name, pic.upload_time as upload_time, IFNULL(pic.update_time, ' ') as update_time, IFNULL(pic.description, ' ') as description, IFNULL(pic.describes, ' ') as describes FROM teach_pic pic, teach_admin admin ");
        sql.append(" WHERE pic.id != '0' ");
        if (!StringUtils.isEmpty(info.get("name"))){
            sql.append(" AND pic.name like '%"+info.get("name")+"%' ");
        }
        if (!StringUtils.isEmpty(info.get("uploader_id"))){
            sql.append(" AND pic.uploader_id = "+info.get("uploader_id")+" ");
        }
        /*String id = info.get("id");
        //System.out.println("id : "+id);
        if(!id.equals("2"))sql.append(" AND uploader_id = "+id+" ");*/
        sql.append(" AND pic.uploader_id = admin.id order by pic.update_time desc");
        System.out.println(sql);
        System.out.println("params : " + params.toString());
        log.info(sql.toString());
        return sql.toString();
    }
}
/*
*/