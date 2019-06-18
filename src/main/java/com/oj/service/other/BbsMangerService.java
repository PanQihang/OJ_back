package com.oj.service.other;
import java.util.Map;
import java.util.List;
public interface BbsMangerService {
    //获取话题列表
    public List<Map> getTopicMaplist();
    ////新增一条话题
    public void addTopic(Map<String, Object> param);

    //修改话题
    public void saveTopic(Map<String, Object> param);

    //删除话题
    public void delTopic(Map<String,  String> param);

    //获取文章列表
    public List<Map> showarticelList(String id);

    //置顶操作
    public void updateFlagpost(Map<String,  String> param);

    //返回文章内容
    public Map<String,Object> showarticel(Map<String,Object> param);
}
