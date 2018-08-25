package com.novel.enumUrl;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.enumUrl
 * @ClassName: EnumUrl
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/22/022 0:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/22/022 0:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum EnumUrl {
    //玄幻
    fantasy("https://www.biquge5.com/shuku/1/allvisit-0-1.html","https://www.biquge5.com/shuku/1/allvisit-0-[0-9]+.html",0),
    //修真
    fixtrue("https://www.biquge5.com/shuku/2/allvisit-0-1.html","https://www.biquge5.com/shuku/2/allvisit-0-[0-9]+.html",1),
    //都市
    urban("https://www.biquge5.com/shuku/3/allvisit-0-1.html","https://www.biquge5.com/shuku/3/allvisit-0-[0-9]+.html",2),
    //穿越
    cross("https://www.biquge5.com/shuku/4/allvisit-0-1.html","https://www.biquge5.com/shuku/4/allvisit-0-[0-9]+.html",3),
    //网游
    online("https://www.biquge5.com/shuku/5/allvisit-0-1.html","https://www.biquge5.com/shuku/5/allvisit-0-[0-9]+.html",4),
    //科幻
    fiction("https://www.biquge5.com/shuku/6/allvisit-0-1.html","https://www.biquge5.com/shuku/6/allvisit-0-[0-9]+.html",5);


    private  String url;
    private  String regex;
    private  int categoryId;
    EnumUrl(String url,String regex, int categoryId) {
        this.url = url;
        this.regex = regex;
        this.categoryId = categoryId;
    }
    public  static EnumUrl getEnumUrl(int categoryId){

        for (EnumUrl e: EnumUrl.values()
             ) {
            if(e.getCategoryId()==categoryId){
              return  e;
            }

        }
        return null;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
