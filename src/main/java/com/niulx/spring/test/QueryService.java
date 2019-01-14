package com.niulx.spring.test;

import com.niulx.spring.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Date 2019-01-14 下午 6:46
 * @Created by nlx
 */
@Service
public class QueryService implements IQueryService {

    /**
     * 查询
     */
    public String query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
        return json;
    }

}

