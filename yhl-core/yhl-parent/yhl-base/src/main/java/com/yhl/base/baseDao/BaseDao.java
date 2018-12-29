package com.yhl.base.baseDao;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.baseorm.dao.JpaBaseDao;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseDao <T extends BaseEntity<ID>,ID extends Serializable>  extends JpaBaseDao<T ,ID> {
}
