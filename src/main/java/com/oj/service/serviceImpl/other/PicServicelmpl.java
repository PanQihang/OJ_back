package com.oj.service.serviceImpl.other;

import com.oj.entity.other.FilePath;
import com.oj.entity.other.Pic;
import com.oj.mapper.other.PicMapper;
import com.oj.service.other.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PicServicelmpl implements PicService {
    @Autowired(required = false)
    private PicMapper mapper;
    public String rootPath;

    public List<Map> getPicMapList(Map<String, String> param)
    {
        //System.out.println(param.toString());
        return mapper.getPicMapList(param);
    }

    public void getRootPath()
    {
        FilePath Path = new FilePath();
        System.out.println(Path);

        rootPath = Path.getUploadPath()+"upload/img/";
        File createPath = new File(rootPath);
        //System.out.println("check my path ---- "+rootPath);
        if(!createPath .exists())
        {
            createPath.mkdirs();
        }
    }
    //上传图片
    public void uploadPic(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception
    {
        getRootPath();
        InputStream in = null;
        OutputStream out = null;
        try
        {
            in = file.getInputStream();
            String id = request.getParameter("id");
            String lastRoute = "";
            if(!id.equals("add"))
            {
                lastRoute = rootPath+mapper.getSaveNameById(id);
            }
            String name = request.getParameter("name");
            String user_id = request.getSession().getAttribute("user_id").toString();
            String describes = request.getParameter("describes");
            String description = request.getParameter("picDesc");
            Date date = new Date();
            String saveName = date.getTime()+file.getOriginalFilename();
            String route = rootPath+saveName;//真正存储路径
            System.out.println(route);
            File targetfile = new File(route);
            out = new FileOutputStream(targetfile);
            FileCopyUtils.copy(in, out);
            Pic M = new Pic();
            M.setName(name);
            M.setUpload_time(date.getTime()/1000);
            M.setUpdate_time(date.getTime()/1000);
            M.setUploader_id(user_id);
            String loadRoute = "/upload/img/"+saveName;
            M.setRoute(loadRoute);//因为不知道怎么映射，暂时这样写死！存在数据库的路径，前台读取这个路径获取图片
            M.setDescription(description);
            M.setDescribes(describes);
            M.setSavename(saveName);
            System.out.println(M);
            if(id.equals("add"))
            {
                mapper.save(M);
            }
            else
            {
                M.setId(id);
                File filePath = new File(lastRoute);
                filePath.delete();
                mapper.update(M);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            throw new Exception("文件上传失败!");
        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            if(out != null)
            {
                try
                {
                    out.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    //新增的话 保存基本的信息且把upload/img/tmp/sessionId/fileName中的图片拷贝到/upload/img/fileName中
    public void savePicMsg(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        getRootPath();
        String id = param.get("id");
        String lastRoute = "";
        if(!id.equals("add"))
        {
            lastRoute = rootPath+mapper.getSaveNameById(id);
        }
        String user_id = request.getSession().getAttribute("user_id").toString();
        String name = param.get("name");
        String describes = param.get("describes");
        String description = param.get("description");
        Pic M = new Pic();
        M.setName(name);
        M.setUploader_id(user_id);
        M.setDescribes(describes);
        M.setDescription(description);
        Date date = new Date();
        M.setUpdate_time(date.getTime()/1000);
        String savename = date.getTime()+param.get("fileName");
        M.setSavename(savename);
        String route = "/upload/img/"+savename;
        M.setRoute(route);
        System.out.println(M.toString());
        String sourcePath = rootPath+"tmp/"+user_id+"/"+param.get("fileName");
        String targetPath = rootPath+savename;
        File source=new File(sourcePath);
        File target=new File(targetPath);
        System.out.println(source+" "+ target);
        source.renameTo(target);
        File del = new File(sourcePath);
        del.delete();
        if(id.equals("add"))
        {
            M.setUpload_time(date.getTime()/1000);
            mapper.savePicMsg(M);
        }
        else
        {
            System.out.println("want to update : "+M.toString());
            M.setId(id);
            File filePath = new File(lastRoute);
            filePath.delete();
            mapper.updatePicMsg(M);
        }
    }


    //上传图片用来展示且只上传到tmp，保存后再上传到对的路径
    public void uploadPicToShow(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception
    {
        getRootPath();
        InputStream in = null;
        OutputStream out = null;
        try
        {
            String user_id = request.getSession().getAttribute("user_id").toString();
            String tmpRoot =  rootPath+"tmp/"+user_id;
            File createTmp = new File(tmpRoot);
            if(!createTmp.exists())
            {
                createTmp.mkdirs();
            }
            in = file.getInputStream();
            String route = rootPath+"tmp/"+user_id+"/"+file.getOriginalFilename();
            System.out.println(route);
            File targetfile = new File(route);
            out = new FileOutputStream(targetfile);
            FileCopyUtils.copy(in, out);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            throw new Exception("图片上传失败!");
        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            if(out != null)
            {
                try
                {
                    out.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    //根据图片ID获取编辑信息
    public List<Map> picEdit(String id)
    {
        return mapper.picEdit(id);
    }

    //保存编辑信息
    public void saveEditPic(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        Date date = new Date();
        Pic M = new Pic();
        String id = param.get("id");
        M.setId(id);
        M.setName(param.get("name"));
        M.setDescribes(param.get("describes"));
        M.setDescription(param.get("description"));
        M.setUpdate_time(date.getTime()/1000);
        M.setRoute(mapper.getPathById(id));
        M.setSavename(mapper.getSaveNameById(id));
        mapper.update(M);
    }
    //删除图片
    public void picDelete(HttpServletRequest request)
    {
        getRootPath();
        String id = request.getParameter("id");
        String route = rootPath+mapper.getSaveNameById(id);
        System.out.println("delete : "+route);
        File filePath = new File(route);
        filePath.delete();
        mapper.delete(id);
    }

    //加载当前文章信息
    public Map<String, String>loadPic(HttpServletRequest request)
    {
        String id = request.getParameter("id");
        System.out.println("come in lmpl "+id);
        Map<String, String> map = mapper.loadPic(id);
        System.out.println("load map: "+map.toString());
        return map;
    }

    //加载所有的轮播
    public List<Map> getSelectPic(HttpServletRequest request)
    {
        return mapper.getSelectPic();
    }

    //加载已选择的轮播
    public List<Map> selectedPicList()
    {
        return mapper.selectedPicList();
    }

    //保存轮播顺序信息
    public void saveAdminPic(@RequestBody Map<String, Object> param, HttpServletRequest request)
    {
        List<Map<String, String>> selectedPicList = (List<Map<String, String>>)param.get("selectedPicList");
        //System.out.println(selectedPicList.toString());
        mapper.clearAdminPic();
        for(int i = 0; i < selectedPicList.size(); i++)
        {
            System.out.println(selectedPicList.get(i));
            System.out.println(String.valueOf(selectedPicList.get(i).get("id")));
            System.out.println(String.valueOf(selectedPicList.get(i).get("order_show")));
            mapper.saveAdminPic(String.valueOf(selectedPicList.get(i).get("id")), String.valueOf(selectedPicList.get(i).get("order_show")));
        }
    }
}
