package com.bear.house.secure.service;

import com.bear.house.secure.SpringBootSecurityApplicationTests;
import com.bear.house.secure.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author WangTao
 *         Created at 18/6/4 下午4:19.
 */
public class UserServiceTest extends SpringBootSecurityApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void findByUserName() throws Exception {
        Optional<User> userOptional = userService.findByUserName("xiaoxiong");
        if (userOptional.isPresent()) {
            System.out.println(userOptional.get());
        }
    }

}