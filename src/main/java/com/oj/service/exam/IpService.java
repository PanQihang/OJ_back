package com.oj.service.exam;

import com.oj.entity.exam.Ip;
import java.util.Map;
import java.util.List;

/**
 * Created by panqihang on 2019/3/21 12:27
 */
public interface IpService {
    //返回Map类型的List
    public List<Map> getIpMapList(Ip ip);

    //通过IPId获取IP信息
    public Map getIpById(String id);

    //IP保存或更新
    public void ipSaveOrUpdate(Ip ip);

    //ip删除
    public void ipDelete(String id);
}
