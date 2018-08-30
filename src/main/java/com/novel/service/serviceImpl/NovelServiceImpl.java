package com.novel.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.dao.NovelMapper;
import com.novel.model.Novel;
import com.novel.service.NovelService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service.serviceImpl
 * @ClassName: NovelServiceImpl
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/11/011 14:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/11/011 14:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Service("NovelService")
@ComponentScan({"com.novel.dao"})
public class NovelServiceImpl implements NovelService {
    @Resource
    private NovelMapper novelMapper;

    /*插入小说信息*/
    @Override
    public void insertNovel(Novel novel) {

        novelMapper.insertNovel(novel);
    }
    /*返回所有小说id*/
    @Override
    public List<Long> findAllNovelId() {
        return novelMapper.findAllNovelId();
    }
    /*返回所有小说*/
    @Override
    public List<Novel> findAllNovel() {
        return novelMapper.findAllNovel();
    }

    /*通过关键字查询小说*/
    @Override
    public List<Novel> findNovelByName(String novelName) {
        return novelMapper.findNovelByName("%"+novelName+"%");
    }


    /*通过novelId查找小说*/
    @Override
    @Cacheable(value = "novel",key = "#novelId")
    public Novel findNovelById(long novelId) {
        System.out.println("qqqq");
        return novelMapper.findNovelById(novelId);

    }
    /*通过Novel实体查询Novel列表*/
    @Override
    public List<Novel> queryNovelList(Novel novel) {
        return novelMapper.queryNovelList(novel);
    }

    @Override
    public PageInfo<Novel> selectAll(Integer page, Integer size,int categoryId ){
        Novel novel = new Novel();
        novel.setCategoryId(categoryId);
        PageHelper.startPage(page,size);
        List<Novel> novelList = novelMapper.queryNovelList(novel);
        PageInfo<Novel> pageInfo =new PageInfo<>(novelList);
        return pageInfo;
    }
}
