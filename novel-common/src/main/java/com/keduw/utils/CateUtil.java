package com.keduw.utils;

/**
 * 小说类别，有新的类别需要在这里添加
 */
public class CateUtil {

    public static final class Info{
        public static final int RECOMMEND = 0; //推荐
        public static final int COATARD = 1;   //修真
        public static final int CITY = 2;      //都市
        public static final int ACROSS = 3;    //穿越
        public static final int FANTASY = 4;   //玄幻
        public static final int SCIENCE = 5;   //科幻
        public static final int GAME = 6;      //网游
    }

    public static int getId(String name, int defaultNum){
        switch (name){
            case "修真小说":
                defaultNum = 1;
                break;
            case "都市小说":
                defaultNum = 2;
                break;
            case "穿越小说":
                defaultNum = 3;
                break;
            case "玄幻小说":
                defaultNum = 4;
                break;
            case "科幻小说":
                defaultNum = 5;
                break;
            case "网游小说":
                defaultNum = 6;
                break;
            default:
                break;
        }
        return defaultNum;
    }
}
