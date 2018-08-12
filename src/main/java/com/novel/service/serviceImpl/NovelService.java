package com.novel.service.serviceImpl;

import com.novel.dao.NovelMapper;
import com.novel.model.Novel;
import com.novel.service.NovelIService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service.serviceImpl
 * @ClassName: NovelService
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
public class NovelService implements NovelIService {
    @Resource
    private NovelMapper novelMapper;

    @Override
    public void insertNovel(Novel novel) {

        novelMapper.insertNovel(novel);
    }

    @Override
    public List<Long> findAllNovelId() {
        return novelMapper.findAllNovelId();
    }

}
