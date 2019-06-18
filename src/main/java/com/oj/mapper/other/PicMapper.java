package com.oj.mapper.other;

import com.oj.entity.other.Pic;
import com.oj.mapper.provider.other.PicProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
/**
 * @author zt
 * @Time 2019年5月16日 13点24分
 * @Description 图片轮播数据库操作接口
 */
@Mapper
public interface PicMapper {

    //通过MyFileProvider类中的getQuerySql()方法动态构建查询语句
    @SelectProvider(type = PicProvider.class, method = "getQuerySql")
    //查询文件列表返回Map类型List
    public List<Map> getPicMapList(@Param("condition") Map<String, String> param);
    //存储图片信息
    @Insert("insert into teach_pic (name, uploader_id, description, describes, upload_time, update_time, route, savename) values(#{name}, #{uploader_id}, #{description}, #{describes}, #{upload_time}, #{update_time}, #{route}, #{savename})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public int save(Pic M);
    //根据id获取编辑信息（）
    @Select("select name, description, describes, route from teach_pic where id = #{id}")
    public List<Map> picEdit(@Param("id") String id);

    //更新信息
    @Update("update teach_pic set name=#{name}, description=#{description}, describes=#{describes}, update_time=#{update_time}, route=#{route}, savename=#{savename} where id = #{id}")
    public void update(Pic M);
    //更新文章信息
    @Update("update teach_pic set name=#{name}, description=#{description}, describes=#{describes}, update_time=#{update_time}, route=#{route}, savename=#{savename} where id = #{id}")
    public void updatePicMsg(Pic M);
    //根据图片ID获取存储路径
    @Select("select route from teach_pic where id = #{id}")
    public String getPathById(String id);
    //根据ID获取存储名字
    @Select("select savename from teach_pic where id = #{id}")
    public String getSaveNameById(String id);
    //根据ID获取上传时间
    @Select("select upload_time from teach_pic where id = #{id}")
    public long getUploadTimeById(String id);
    //根据ID获取上传时间
    @Select("select update_time from teach_pic where id = #{id}")
    public long getUpdateTimeById(String id);
    //删除图片信息
    @Delete("delete from teach_pic where id = #{id}")
    public void delete(String id);

    //根据ID查询轮播信息
    @Select("select teach_pic.name as name, teach_pic.description as description, teach_pic.describes as describes, teach_pic.route as route from teach_pic where id = #{id}")
    public Map<String, String>loadPic(@Param("id") String id);

    //加载所有的轮播
    @Select("select teach_pic.id as id, teach_pic.name as name, teach_admin.name as uploader_name, teach_pic.describes as describes from teach_pic, teach_admin where teach_admin.id = teach_pic.uploader_id")
    public List<Map>getSelectPic();

    //加载已选择的轮播
    @Select("select teach_pic.order_show as order_show, teach_pic.id as id, teach_pic.name as name, teach_admin.name as uploader_name, teach_pic.describes as describes from teach_pic, teach_admin where teach_pic.order_show != 0 and teach_admin.id = teach_pic.uploader_id order by teach_pic.order_show asc")
    public List<Map> selectedPicList();

    //清空轮播列表 就是把所有的order_show置为0
    @Update("update teach_pic set order_show = 0")
    public void clearAdminPic();

    //置轮播顺序
    @Update("update teach_pic set order_show = #{order_show} where id = #{id}")
    public void saveAdminPic(@Param("id") String id, @Param("order_show") String order_show);

    //保存文章信息
    @Insert("insert into teach_pic (name, uploader_id, description, describes, upload_time, update_time, route, savename) values(#{name}, #{uploader_id}, #{description}, #{describes}, #{upload_time}, #{update_time}, #{route}, #{savename})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void savePicMsg(Pic M);

}
