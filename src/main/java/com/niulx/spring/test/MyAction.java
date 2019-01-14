package com.niulx.spring.test;

import com.niulx.spring.annotation.Autowired;
import com.niulx.spring.annotation.Controller;
import com.niulx.spring.annotation.RequestMapping;


/**
 * @Date 2019-01-14 下午 6:48
 * @Created by nlx
 */

@Controller
@RequestMapping("/web")
public class MyAction {

    @Autowired
    IQueryService queryService;

    @Autowired
    IModifyService modifyService;

    @Autowired
    PageAction pageAction;


    @RequestMapping("/query.json")
    public void query() {
        String result = queryService.query("hello");
        System.out.println(result);
    }


}
