package com.novel.model;

/**
  *
  * @Package:        com.novel.model
  * @ClassName:
  * @Description:    java类作用描述:小说信息表
  * @Author:         林浩东
  * @CreateDate:     2018/8/11/011 14:05
  * @UpdateUser:     林浩东
  * @UpdateDate:     2018/8/11/011 14:05
  * @UpdateRemark:   更新说明：无
  * @Version:        1.0
 */
public class Novel {
    private long    novelId;    //小说id
    private String novelName;   //小说名字
    private String author;      //作者
    private int categoryId;    //类别id
    private String brief;       //小说简介
    private String status;      //状态（连载/完结）
    private String lastTime;    //最后更新时间
    private String novelUrl;    //小说链接

    public Novel(){

    }

    public long getNovelId() {
        return novelId;
    }

    public void setNovelId(long novelId) {
        this.novelId = novelId;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getNovelUrl() {
        return novelUrl;
    }

    public void setNovelUrl(String novelUrl) {
        this.novelUrl = novelUrl;
    }



    @Override
    public String toString() {
        return "Novel{" +
                "novelId=" + novelId +
                ", novelName='" + novelName + '\'' +
                ", author='" + author + '\'' +
                ", categoryId=" + categoryId +
                ", brief='" + brief + '\'' +
                ", status='" + status + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", novelUrl='" + novelUrl + '\'' +
                '}';
    }
}
