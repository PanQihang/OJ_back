package com.oj.service.system;

import java.util.List;
import java.util.Map;

/**
 * Created by panqihang on 2019/4/12 16:56
 */
public interface HomepageService {
    //返回待办项列表
    public List<Map> getPending(String id);

    //返回提交统计
    public List<Map> getSubmit();

}
