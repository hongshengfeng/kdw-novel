package com.novel.model;

import java.io.Serializable;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.model
 * @ClassName: CategoryInfo
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/16/016 23:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/16/016 23:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CategoryInfo implements Serializable {
    private static final long serialVersionUID = -5809782578272943991L;
    private int categoryId;
    private String category;

    public int getCategoryid() {
        return categoryId;
    }

    public void setCategoryid(int categoryid) {
        this.categoryId = categoryid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
