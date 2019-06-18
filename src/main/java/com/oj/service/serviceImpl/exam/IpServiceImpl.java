package com.oj.service.serviceImpl.exam;

import com.oj.entity.exam.Ip;
import com.oj.mapper.exam.IpMapper;
import com.oj.service.exam.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by panqihang on 2019/3/21 13:04
 */
@Service
public class IpServiceImpl implements IpService
{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //注入UserMapper
    @Autowired(required = false)
    private IpMapper mapper;

    @Override
    public List<Map> getIpMapList(Ip ip) {
        return mapper.getIpMaplist(ip);
    }

    @Override
    public Map getIpById(String id) {
        return mapper.getIpById(id).get(0);
    }

    @Override
    public void ipSaveOrUpdate(Ip ip){
        //若角色ID为空进行插入操作，否则进行更新操作
        if(StringUtils.isEmpty(ip.getId())){
            mapper.save(ip);
        }else {
            mapper.update(ip);
        }
    }

    @Override
    public void ipDelete(String id) {
        mapper.ipDelete(id);
    }
}
