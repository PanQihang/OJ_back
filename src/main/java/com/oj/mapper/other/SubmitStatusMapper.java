package com.oj.mapper.other;
import com.oj.entity.other.SubmitCodeList;
import com.oj.entity.other.SubmitStatus;
import org.apache.ibatis.annotations.*;
import com.oj.mapper.provider.other.SubmitStatusProvider;
import java.util.List;
import java.util.Map;
@Mapper
public interface SubmitStatusMapper {
    //获取全部主题列表
    //@Select("SELECT a.problem_id,b.name,a.submit_state,a.submit_language,a.submit_time,a.submit_memory,a.submit_code_length,a.submit_date FROM teach_submit_code as a,teach_students as b where a.user_id=b.id order by submit_date desc limit 100")
    @SelectProvider(type=SubmitStatusProvider.class, method = "getQuerySql")
    public List<SubmitCodeList> getSubmitStatusMaplist(@Param("condition")Map<String, String> params);
    // 查询总数
    @Select("SELECT COUNT(*) FROM teach_submit_code AS a, teach_students AS b WHERE a.user_id = b.id")
    public int selectTotalCount();
    @SelectProvider(type=SubmitStatusProvider.class, method = "selectRecordsFiltered")
    // 根据条件获取筛选后的总数
    public int selectRecordsFiltered(@Param("condition")Map<String, String> params);

}
