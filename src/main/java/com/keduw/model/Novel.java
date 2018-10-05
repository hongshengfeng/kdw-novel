package com.keduw.model;

/**
 * 小说信息
 * @author hsfeng
 */
public class Novel {
    private Integer novelid;

    private String novelname;

    private String author;

    private Integer categoryid;

    private String status;

    private String lasttime;

    private String novelurl;

    private Integer chaptersize;

    private String brief;

    public Integer getNovelid() {
        return novelid;
    }

    public void setNovelid(Integer novelid) {
        this.novelid = novelid;
    }

    public String getNovelname() {
        return novelname;
    }

    public void setNovelname(String novelname) {
        this.novelname = novelname == null ? null : novelname.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime == null ? null : lasttime.trim();
    }

    public String getNovelurl() {
        return novelurl;
    }

    public void setNovelurl(String novelurl) {
        this.novelurl = novelurl == null ? null : novelurl.trim();
    }

    public Integer getChaptersize() {
        return chaptersize;
    }

    public void setChaptersize(Integer chaptersize) {
        this.chaptersize = chaptersize;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }
}