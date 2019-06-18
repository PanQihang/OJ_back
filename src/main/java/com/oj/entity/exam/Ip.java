package com.oj.entity.exam;

/**
 * Created by panqihang on 2019/3/21 12:03
 */
public class Ip {
    //ip id
    private String id;
    //ip机房位置
    private String location;
    //ip
    private String ip;
    //ip描述
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
