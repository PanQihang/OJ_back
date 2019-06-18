package com.oj.mapper.other;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
@Mapper
public interface BbsMangerMapper {
    //获取全部主题列表
    @Select("SELECT a.*,b.name as admin_name FROM teach_bbs_topic a,teach_admin b where a.admin_id=b.id")
    public List<Map> getTopicMaplist();
    //新增一条话题
    @Select("insert into teach_bbs_topic(id,name,content,admin_id) values(NULL,#{name},#{content},#{admin_id})")
    public void addTopic(Map<String, Object> param);
    //修改一条话题
    @Select("update teach_bbs_topic set name=#{name},content=#{content} where id=#{id}")
    public void saveTopic(Map<String,Object> param);
    //删除话题
    @Select("select id from teach_bbs_post where sub_id=#{id}")
    public List<Map> selectpostId(String id);
    @Select("delete from teach_bbs_post where id=#{id}")
    public void deletePost(String id);
    @Select("delete from teach_bbs_reply where post_id=#{id}")
    public void delreply(String id);
    @Select("delete from teach_bbs_post_zan where post_id=#{id}")
    public void delpostzan(String id);
    @Select("delete from teach_bbs_topic where id=#{id}")
    public void delTopic(String id);
    //文章列表
    @Select("select id,user_id,title,content,flag,view_num,zan_num,reply_num,identity,FROM_UNIXTIME(creat_time) as time from teach_bbs_post where sub_id=#{id}\n" +
            "ORDER BY view_num desc,zan_num DESC,reply_num desc")
    public List<Map> showarticelList(String id);

    //置顶操作
    @Select("update teach_bbs_post set flag=#{flag} where id=#{id}")
    public void updateFlagpost(Map<String,  String> param);

    @Select("select * from teach_bbs_post where id=#{id}")
    public Map<String,Object> showarticel(Map<String,Object> param);
}
