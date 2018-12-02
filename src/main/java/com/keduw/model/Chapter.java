package com.keduw.model;

//章节信息
public class Chapter {
    private Integer id;  //章节id

    private Integer nId; //小说id

    private String name; //章节名称

    private String content; //章节内容

    private String link; //章节链接

    private Byte isFull; //是否数据完整

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Byte getIsFull() {
        return isFull;
    }

    public void setIsFull(Byte isFull) {
        this.isFull = isFull;
    }
}