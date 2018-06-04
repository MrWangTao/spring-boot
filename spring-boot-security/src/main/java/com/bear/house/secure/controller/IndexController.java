package com.bear.house.secure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangTao
 *         Created at 18/6/4 下午2:59.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping("index")
    public String index() {
        return "欢迎来到小熊的世界";
    }

    @GetMapping("hello")
    public String hello(String name) {
        return "Hello " + name;
    }

}
