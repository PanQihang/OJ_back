package com.oj.entity.other;
/**
 * @author zt
 * @Time 2019年5月16日 13点24分
 * @Description 图片轮播实体类
 */
public class Pic {

    //ID
    private String id;
    //文章名称
    private String name;
    //上传时间
    private long upload_time;
    //更改时间
    private long update_time;
    //上传教师id
    private String uploader_id;
    //存储图片路径
    private String route;
    //文章内容
    private String description;
    //备注
    private String describes;
    //实际存储文件名字
    private String savename;
    //轮播顺序
    private String order_show;

    public long getUpdate_time(){return update_time;}
    public void setUpdate_time(long update_time){this.update_time=update_time;}
    public String getOrder_show(){return order_show;}
    public void setOrder_show(String order_show){this.order_show=order_show;}

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

    public String getDescription()
    {
        return description;
    }

    public String getDescribes(){return describes;}
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
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setDescribes(String describes)
    {
        this.describes = describes;
    }

    public String toString()
    {
        return "name: " + name + " uploader_id: "+uploader_id+" describes: "+describes+" description: "+description+" upload_time: "+upload_time+" update_time: "+update_time+" route: " + route + " savename: "+savename;
        //return name + " - " + upload_time + " - " + uploader_id + " - " + route + " - " + description + "-" + describes;
    }

}
