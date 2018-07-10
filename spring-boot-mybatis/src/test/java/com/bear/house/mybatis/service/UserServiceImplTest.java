package com.bear.house.mybatis.service;

import com.bear.house.mybatis.SpringBootMybatisApplicationTests;
import com.bear.house.mybatis.entity.User;
import com.bear.house.mybatis.enums.StatusEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author WangTao
 *         Created at 18/7/10 上午11:37.
 */
public class UserServiceImplTest extends SpringBootMybatisApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void saveUser() throws Exception {
    }

}