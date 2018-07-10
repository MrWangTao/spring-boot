package com.bear.house.mybatis.service;

import com.bear.house.mybatis.entity.User;
import com.bear.house.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangTao
 *         Created at 18/7/10 上午11:03.
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

}
