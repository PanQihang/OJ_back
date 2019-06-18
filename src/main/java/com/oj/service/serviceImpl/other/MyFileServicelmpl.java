package com.oj.service.serviceImpl.other;

import com.oj.entity.other.MyFile;
import com.oj.entity.other.FilePath;
import com.oj.mapper.other.MyFileMapper;
import com.oj.service.other.MyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Date;

@Service
public class MyFileServicelmpl implements MyFileService {
    @Autowired(required = false)
    private MyFileMapper mapper;
    public String rootPath;

    public void getRootPath()
    {
        FilePath Path = new FilePath();
        System.out.println(Path);
        /*System.out.println("===========操作系统是:"+System.getProperties().getProperty("os.name"));
        if(Pattern.matches(".*(Win).*", System.getProperties().getProperty("os.name")))
        {
            //System.out.println("check my winPath ---- "+Path.getWinPath());
            rootPath = Path.getWinPath();
        }
        else
        {
            //System.out.println("check my winPath ---- "+Path.getLinuxPath());
            rootPath = Path.getLinuxPath();
        }*/

        rootPath = Path.getUploadPath()+"upload/resource/";
        File createPath = new File(rootPath);
        //System.out.println("check my path ---- "+rootPath);
        if(!createPath .exists())
        {
            createPath.mkdirs();
        }
    }

    public List<Map> getAdminSelectInfo()
    {
        return mapper.getAdminSelectInfo();
    }

    public List<Map> getFileMapList(Map<String, String> param)
    {
        System.out.println(param.toString());
        return mapper.getFileMapList(param);
    }

    //根据文件ID获取文件名
    public List<Map> fileFlag(String id)
    {
        return mapper.getFileNameAndNameById(id);
    }

    //保存状态
    public void saveFileFlag(String id, String flag)
    {
        mapper.saveFileFlag(id, flag);
    }

    //检查文件名字
    public void checkFileName(String name, String id) throws Exception
    {
        if(mapper.getFileRouteByName(name, id).size() > 0){
            throw new Exception("当前文件名已存在!");
        }
    }

    //上传文件
    public void uploadMyFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception
    {
        getRootPath();
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = file.getInputStream();
                String id = request.getSession().getAttribute("user_id").toString();
                int flag = Integer.valueOf(request.getParameter("flag"));

                //判断是否有该用户的文件目录，无则创建以此来让文件能够顺利存储到该用户目录下
                String []list = new File(rootPath).list();
                int pathLength = list.length;
                boolean createFlag = false;
                for(int i = 0; i < pathLength; i++)
                {
                    if(list[i] == id)
                    {
                        createFlag = true;
                    }
                }
                if(!createFlag)
                {
                    String newRootPath = rootPath+id;
                    System.out.println(newRootPath);
                    File createPath = new File(newRootPath);
                    createPath.mkdirs();
                }
                Date date = new Date();
                String saveName = date.getTime()+file.getOriginalFilename();
                String route = rootPath+id+"/"+saveName;
                System.out.println(route);
                File targetfile = new File(route);
                out = new FileOutputStream(targetfile);
                FileCopyUtils.copy(in, out);
                MyFile M = new MyFile();
                M.setName(file.getOriginalFilename());
                M.setUpload_time(new Date().getTime()/1000);
                M.setUploader_id(id);
                M.setRoute(route);
                M.setSize(file.getSize());
                M.setFlag(flag);
                M.setSavename(saveName);
                System.out.println(M);
                mapper.save(M);
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

    public void downloadFile(String id, HttpServletResponse response)
    {
        getRootPath();
        String path = mapper.getPathById(id);
        //System.out.println(path);
        String fileName = java.net.URLEncoder.encode(mapper.getFileNameById(id));
        File file = new File(path);
        if (file.exists()) {
            //System.out.println("File path is : " + path);
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Length", "" + mapper.getFileSizeById(id));
            //response.setHeader("Pragma", "No-cache");
            //response.setHeader("Cache-Control", "No-cache");
            //response.setDateHeader("Expires", 0);
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            FileInputStream fis = null;
            try {
                OutputStream os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    //os.flush();
                    i = bis.read(buff);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void fileDelete(HttpServletRequest request)
    {
        String userId = request.getSession().getAttribute("user_id").toString();
        String id = request.getParameter("id");
        System.out.println("session id "+userId);
        System.out.println("id "+id);
        String route = mapper.getPathById(id);
        File filePath = new File(route);
        filePath.delete();
        mapper.fileDelete(id);
    }

    public boolean checkFileExistence(String id)
    {
        String path = mapper.getPathById(id);
        File file = new File(path);
        if(file.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
