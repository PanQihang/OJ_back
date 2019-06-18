package com.oj.mapper.education;

import com.oj.entity.education.Notice;
import com.oj.mapper.provider.education.NoticeProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by AC on 2019/4/2 14:46
 */
@Mapper
public interface NoticeMapper {
    //获取全部notice列表
    @SelectProvider(type= NoticeProvider.class, method = "getQuerySql")
    public List<Map> getNoticeMaplist(@Param("condition") Notice notice);

    //通过id查询通知信息
    @Select("select * from teach_notice where id=#{id}")
    public List<Map> getNoticeById(String id);

    //新增通知信息
    @Insert("insert into teach_notice(title, author, time, content) values(#{title}, #{author}, #{time}, #{content}) ")
    public int save(Notice notice);

    //更新通知信息
    @Update("update teach_notice set title=#{title}, author=#{author}, time=#{time}, content=#{content} where id=#{id}")
    public int update(Notice notice);

    //通过id删除
    @Delete("delete from teach_notice where id=#{id}")
    public void noticeDelete(String id);

}
