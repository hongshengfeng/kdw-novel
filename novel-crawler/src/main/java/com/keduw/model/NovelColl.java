package com.keduw.model;

import java.util.List;

/**
 * 存储爬虫的小说信息和章节
 */
public class NovelColl {

    private Novel novel; //小说信息
    private List<Chapter> chapters; //章节信息

    public Novel getNovel() {
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
