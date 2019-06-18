package com.oj.entity.education;

/**
 * Created by AC on 2019/4/2 14:38
 */
public class Notice {
    //notice id
    private String id;

    //notice 作者
    private String author;

    //发布时间time
    private String time;

    //标题
    private String title;

    //内容
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
