package com.oj.mapper.exam;

import com.oj.entity.exam.Ip;
import com.oj.mapper.provider.exam.IpProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by panqihang on 2019/3/21 14:12
 */
//注册Mapper
@Mapper
public interface IpMapper {
    //获取全部ip列表
    //@Select("select (@i:=@i+1)num,s.* from teach_ip s,(select @i:=0)t where s.ip!='32'")
    @SelectProvider(type= IpProvider.class, method = "getQuerySql")
    public List<Map> getIpMaplist(@Param("condition") Ip ip);

    //通过id查询角色信息
    @Select("select * from teach_ip where id=#{id}")
    public List<Map> getIpById(String id);

    //新增IP信息
    @Insert("insert into teach_ip(ip, location, content) values(#{ip}, #{location}, #{content}) ")
    public int save(Ip ip);

    //更新IP信息
    @Update("update teach_ip set ip=#{ip}, location=#{location},content=#{content} where id=#{id}")
    public int update(Ip ip);

    //ip通过id删除
    @Delete("delete from teach_ip where id=#{id}")
    public void ipDelete(String id);
}
