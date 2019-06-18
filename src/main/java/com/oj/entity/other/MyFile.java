package com.oj.entity.other;
/**
 * @author zt
 * @Time 2019年4月4日 15点00分
 * @Description 对应数据库中文件表实体类
 */
public class MyFile {
    //文件ID
    private String id;
    //文件名称
    private String name;
    //上传时间
    private long upload_time;
    //上传教师id
    private String uploader_id;
    //存储文件路径
    private String route;
    //大小
    private long size;
    //标记（是公开还是私有）
    private int flag;
    //实际存储文件名字
    private String savename;

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public long getUpload_time()
    {
        return upload_time;
    }
    public String getUploader_id()
    {
        return uploader_id;
    }

    public String getRoute()
    {
        return route;
    }

    public long getSize()
    {
        return size;
    }

    public int getFlag(){return flag;}
    public String getSavename(){return savename;}


    public void setSavename(String savename){this.savename = savename;}
    public void setId(String Course_id)
    {
        this.id = Course_id;
    }
    public void setName(String Course_name)
    {
        this.name = Course_name;
    }
    public void setUpload_time(long upload_time)
    {
        this.upload_time = upload_time;
    }
    public void setUploader_id(String uploader_id)
    {
        this.uploader_id = uploader_id;
    }
    public void setRoute(String route)
    {
        this.route = route;
    }
    public void setSize(long size){this.size = size;}
    public void setFlag(int flag){this.flag = flag;}

    public String toString()
    {
        return name + " - " + upload_time + " - " + uploader_id + " - " + route + " - " + flag;
    }

}
