package com.oj.service.testing;
import com.oj.entity.testing.Subject;
import java.util.List;
import java.util.Map;

/**
 * @author liyue
 * @Time 2019年3月27日 13点16分
 * @Description 主题功能相关功能Service接口
 *
 */
public interface SubjectService {

    //获取主题信息列表
    public List<Map> getSubjectMaplist();

    //保存主题信息
    public void subjectSave(Subject subject);

    //主题更新
   public void subjectUpdate(Subject subject);

    //通过id获取主题信息
    public Map getSubjectById(String id);
    //主题删除
    public void subjectDelete(String id);

    //获取主题Id列表
//    public List<String> getSubjectIds(String id);
}
