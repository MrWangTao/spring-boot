package com.bear.house.mybatis.controller;

import com.bear.house.mybatis.entity.User;
import com.bear.house.mybatis.enums.StatusEnum;
import com.bear.house.mybatis.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author WangTao
 *         Created at 18/7/10 上午11:57.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/save")
    public String saveUser() {
        String s = UUID.randomUUID().toString();
        String id = s.replaceAll("-", "");
        User user = User.builder()
                .id(id)
                .userName("test1")
                .name("test")
                .password("12345678")
                .salt("000000000")
                .status(StatusEnum.ENABLE)
                .note("test mybatis enum")
                .build();
        userService.saveUser(user);
        System.out.println("success");
        return "success";
    }

}
