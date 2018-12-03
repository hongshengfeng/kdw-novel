package com.keduw.service.impl;

import com.keduw.dao.UserMapper;
import com.keduw.model.User;
import com.keduw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.service.impl
 * @ClassName: UserServiceImpl
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/12/3/003 23:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/3/003 23:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

}
