package com.yhl.baseOrm.dao;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseDao<T,ID extends Serializable> extends JpaBaseDao<T,ID> {

}
