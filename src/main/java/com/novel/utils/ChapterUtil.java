package com.novel.utils;

import com.novel.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterUtil {

    public static List<Chapter> containChapter(List<Chapter> l1,int size){
        int tmp = l1.size()-size;
        List<Chapter> result = new ArrayList<>();
        for (int i = 1; i <=tmp ; i++) {
            result.add(l1.get(size+i));
        }


        return result;
    };
}
