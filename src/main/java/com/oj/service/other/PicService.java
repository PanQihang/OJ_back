package com.oj.service.other;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
/**
 * @author zt
 * @Time 2019年5月16日 13点24分
 * @Description 图片轮播service接口
 */
public interface PicService {
    // 获取图片列表
    public List<Map> getPicMapList(Map<String, String> param);
    //上传图片
    public void uploadPic(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception;
    //上传图片用来展示且只上传到tmp，保存后再上传到对的路径
    public void uploadPicToShow(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception;
    //根据图片ID获取编辑信息
    public List<Map> picEdit(String id);
    //保存编辑信息(未更新图片的时候调用的)
    public void saveEditPic(@RequestBody Map<String, String> param, HttpServletRequest request);
    //删除图片
    public void picDelete(HttpServletRequest request);
    //加载当前图片轮播信息
    public Map<String, String>loadPic(HttpServletRequest request);
    //加载所有选择的轮播
    public List<Map> getSelectPic(HttpServletRequest request);
    //加载已选择的轮播
    public List<Map> selectedPicList();
    //保存轮播顺序信息
    public void saveAdminPic(@RequestBody Map<String, Object> param, HttpServletRequest request);
    //新增的话 保存基本的信息且把upload/img/tmp/sessionId/fileName中的图片拷贝到/upload/img/fileName中
    public void savePicMsg(@RequestBody Map<String, String> param, HttpServletRequest request);
}
