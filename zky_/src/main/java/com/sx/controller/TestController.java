package com.sx.controller;

import com.sx.util.Hello;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yayu
 * @title: TestController
 * @description: TODO
 * @date 2020/11/19 13:39
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/hello")
    public String test() {
        Hello hello = new Hello();
        hello.test();
        return "123";
    }
}
