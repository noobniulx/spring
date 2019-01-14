package com.niulx.spring.test;

/**
 * @Date 2019-01-14 下午 7:26
 * @Created by nlx
 */
public interface IModifyService {

    /**
     * 增加
     */
    String add(String name, String addr);

    /**
     * 修改
     */
    String edit(Integer id, String name);

    /**
     * 删除
     */
    String remove(Integer id);

}
