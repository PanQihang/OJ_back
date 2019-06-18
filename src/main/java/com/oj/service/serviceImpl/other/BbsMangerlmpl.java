package com.oj.service.serviceImpl.other;

import com.oj.mapper.other.BbsMangerMapper;
import com.oj.mapper.other.BbsMapper;
import com.oj.service.other.BbsMangerService;
import com.oj.service.other.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BbsMangerlmpl implements BbsMangerService {
    @Autowired(required = false)
    private BbsMangerMapper mapper;
    @Autowired(required = false)
    private BbsMapper mapper1;
    //获取话题列表
    @Override
    public List<Map> getTopicMaplist() {
        List<Map> list = mapper.getTopicMaplist();
        return list;
    }
    //新增一条话题
    public void addTopic(Map<String, Object> param){
        mapper.addTopic(param);
    }

    //修改话题
    public void saveTopic(Map<String, Object> param){
        mapper.saveTopic(param);
    }

    //删除话题
    public void delTopic(Map<String, String> param){
        List<Map> list= new ArrayList<>();
         String id=param.get("id");
        list=mapper.selectpostId(id);
        int i=0;
        for(i=0;i<list.size();i++){
            mapper.deletePost(list.get(i).get("id").toString());
            mapper.delreply(list.get(i).get("id").toString());
            mapper.delpostzan(list.get(i).get("id").toString());
        }
        mapper.delTopic(id);
    }

    //获取文章列表
    public List<Map> showarticelList(String id){
        List<Map> list=mapper.showarticelList(id);
        int i=0;
        String name="";
        for(i=0;i<list.size();i++){
            if(list.get(i).get("flag").toString().equals("1")){
                list.get(i).put("flag","是");
            }else{
                list.get(i).put("flag","否");
            }
            if(list.get(i).get("identity").toString().equals("1")){
                name=mapper1.getadminname(list.get(i).get("user_id").toString());
            }else{
                name=mapper1.getstudentname(list.get(i).get("user_id").toString());
            }
            list.get(i).put("name",name);
        }
        return list;
    }

    //置顶操作
    public void updateFlagpost(Map<String,  String> param){
        mapper.updateFlagpost(param);
    }
    //返回文章内容
    public Map<String,Object> showarticel(Map<String,Object> param){
        return mapper.showarticel(param);
    }
}
