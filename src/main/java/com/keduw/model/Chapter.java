package com.keduw.model;

/**
 * 章节信息
 */
public class Chapter {
    private Integer chapterid;

    private Integer novelid;

    private String chapter;

    private String chapterurl;

    private String content;

    public Integer getChapterid() {
        return chapterid;
    }

    public void setChapterid(Integer chapterid) {
        this.chapterid = chapterid;
    }

    public Integer getNovelid() {
        return novelid;
    }

    public void setNovelid(Integer novelid) {
        this.novelid = novelid;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter == null ? null : chapter.trim();
    }

    public String getChapterurl() {
        return chapterurl;
    }

    public void setChapterurl(String chapterurl) {
        this.chapterurl = chapterurl == null ? null : chapterurl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}