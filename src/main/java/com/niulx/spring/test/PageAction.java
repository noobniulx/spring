package com.niulx.spring.test;

import com.niulx.spring.annotation.Autowired;
import com.niulx.spring.annotation.Controller;
import com.niulx.spring.annotation.RequestMapping;

/**
 * @Date 2019-01-14 下午 7:32
 * @Created by nlx
 */
@Controller
@RequestMapping("/web")
public class PageAction {

    @Autowired
    IQueryService queryService;

    @Autowired
    MyAction myAction;

    @RequestMapping("/first.html")
    public void query() {
        System.out.println("hello world");
    }


}
