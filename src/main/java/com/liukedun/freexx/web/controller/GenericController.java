package com.liukedun.freexx.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-08 01:04
 */
@RestController
public class GenericController {

    @RequestMapping(value = "/")
    public String defaultResponse() {
        return "It works!";
    }

    @RequestMapping(value = "test_add_container",method = RequestMethod.POST)
    public String testAddContainer() {
        return "added";
    }

    @RequestMapping(value = "ping_all_client")
    public String pingAllClient() {
        return null;
    }

}
