package com.oj.mapper.competition;
import com.oj.entity.competition.Competition;
import org.apache.ibatis.annotations.*;
import com.oj.mapper.provider.competition.CompetitionProvider;
import java.util.List;
import java.util.Map;
@Mapper
public interface CompetitionMapper {
    @SelectProvider(type=CompetitionProvider.class, method = "getcompetitionInfoSql")
    public List<Map> getcompetitionInfo(@Param("condition")Map<String, String> params);

    //获取试题信息列表
    @Select("SELECT id, name, IF(public='on','是','否') as isShow FROM teach_problems where name !='' ORDER BY id DESC")
    public List<Map> loadPreSelectQuestion();

    //获取机房列表
    @Select("SELECT id, location, ip FROM teach_ip ORDER BY id DESC")
    public List<Map> loadPreSelectJroom();
    //通过ID获取实验信息
    @Select("SELECT id, name, FROM_UNIXTIME(start) as start, FROM_UNIXTIME(end) as end, is_ip, only_ip, description  FROM teach_test WHERE id = #{id}")
    public Map getCompInfoById(String id);
    //通过ID获取已选择试题信息
    @Select("SELECT b.id, b.name, a.score FROM teach_test_problems a INNER JOIN teach_problems b on a.pid = b.id WHERE a.tid = #{id} ORDER BY b.id")
    public List<Map> getSelectedQueListById(String id);

    //通过ID获取已选择机房信息
    @Select("SELECT b.id, b.location, b.ip FROM teach_test_ip a INNER JOIN teach_ip b on a.iid = b.id where a.tid = #{id} ORDER BY b.id")
    public List<Map> getSelectedJroomListById(String id);

    //保存竞赛信息
    @Insert("INSERT INTO teach_test (NAME, START, END, description, kind, is_ip, only_ip, report, admin_id) " +
            "VALUES(#{name},UNIX_TIMESTAMP(#{start}),UNIX_TIMESTAMP(#{end}),#{description},#{kind},#{isIp},#{onlyIp},#{report},#{adminId})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void saveComp(Competition compInfo);

    @Insert("INSERT INTO teach_test_problems(tid, pid, score) VALUES(#{experId}, #{id}, #{score})")
    public void saveSelectedQue(Map<String,String> selectedQue);


    @Insert("INSERT INTO teach_test_ip(tid, iid) VALUES(#{experId}, #{id})")
    public void saveSelectedJroom(Map<String,String> selectedJroom);

    @Delete("DELETE FROM teach_test_problems where tid = #{id}")
    public void deleteQue(String id);


    @Delete("DELETE FROM teach_test_ip where tid = #{id}")
    public void deleteJroom(String id);
    @Delete("DELETE FROM teach_test where id = #{id}")
    public void deleteComp(String id);

    @Update("UPDATE teach_test SET name=#{name}, start=UNIX_TIMESTAMP(#{start}), end=UNIX_TIMESTAMP(#{end}), description=#{description}, only_ip=#{onlyIp}, is_ip=#{isIp} where id=#{id}")
    public void updateComp(Competition experInfo);
}
