package com.novel.model;

import java.io.Serializable;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.model
 * @ClassName: Chapter
 * @Description: java类作用描述：章节信息以及主要内容
 * @Author: 林浩东
 * @CreateDate: 2018/8/12/012 1:22
 * @UpdateUser: 林浩东
 * @UpdateDate: 2018/8/12/012 1:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Chapter implements Serializable {
    private static final long serialVersionUID = -5309782578272943999L;
    private int chapterId;
    private long novelId;
    private String chapter;
    private String content;
    private String chapterUrl;

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public long getNovelId() {
        return novelId;
    }

    public void setNovelId(long novelId) {
        this.novelId = novelId;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId=" + chapterId +
                ", novelId=" + novelId +
                ", chapter='" + chapter + '\'' +
                ", content='" + content + '\'' +
                ", chapterUrl='" + chapterUrl + '\'' +
                '}';
    }
}
