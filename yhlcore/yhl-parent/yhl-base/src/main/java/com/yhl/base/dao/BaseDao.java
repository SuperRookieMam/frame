package com.yhl.base.dao;

import com.yhl.basedao.dao.JpaBaseDao;

import java.io.Serializable;

public interface BaseDao<T,ID extends Serializable> extends JpaBaseDao<T,ID> {

}
