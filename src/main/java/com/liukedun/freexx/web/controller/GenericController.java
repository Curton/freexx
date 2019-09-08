package com.liukedun.freexx.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CoveyLiu, covey@liukedun.com
 * @date 2019-09-08 01:04
 */
@RestController
public class GenericController {

    @RequestMapping(value = "/")
    public String defaultResponse() {
        return "hello";
    }

}
