package com.yhl.service.impl;

import com.yhl.dao.BaseDao;
import com.yhl.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private BaseDao<T> baseDao;


}
