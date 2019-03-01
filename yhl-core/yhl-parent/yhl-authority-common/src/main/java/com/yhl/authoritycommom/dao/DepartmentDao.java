package com.yhl.authoritycommom.dao;

import com.yhl.authoritycommom.entity.Department;
import com.yhl.base.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends BaseDao<Department,String> {
}
