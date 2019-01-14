package com.niulx.spring.test;

import com.niulx.spring.annotation.Service;

/**
 * @Date 2019-01-14 下午 7:27
 * @Created by nlx
 */
@Service
public class ModifyService implements IModifyService {

    @Override
    public String add(String name, String addr) {
        return null;
    }

    @Override
    public String edit(Integer id, String name) {
        return null;
    }

    @Override
    public String remove(Integer id) {
        return null;
    }
}
