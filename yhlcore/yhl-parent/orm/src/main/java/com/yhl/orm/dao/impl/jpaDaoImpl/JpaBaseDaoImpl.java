package com.yhl.orm.dao.impl.jpaDaoImpl;

import com.yhl.orm.constant.PageInfo;
import com.yhl.orm.constant.Params;
import com.yhl.orm.dao.jpaDao.JpaBaseDao;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
//@NoRepositoryBean ：启动时不初始化该实体类。是spring date jpa的一种注解
@NoRepositoryBean
public class JpaBaseDaoImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID > implements JpaBaseDao<T,ID> {

    private EntityManager entityManager;

    public JpaBaseDaoImpl(JpaEntityInformation<T, ?> tJpaEntityInformation, EntityManager entityManager){
        super(tJpaEntityInformation, entityManager);
    }
    //父类没有不带参数的构造方法，这里手动构造父类
    public JpaBaseDaoImpl(Class<T> entityClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getMetadata(entityClass,entityManager), entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public <T> T findById(ID id) {
        return (T) super.findOne(id);
    }

    @Override
    public <T> List<T> findByParams(Params params) {
        return null;
    }

    @Override
    public int findContByParams(Params params) {
        return 0;
    }

    @Override
    public <T> PageInfo<T> findPageByParams(Params params) {
        return null;
    }

    @Override
    public Object findByHql(String hql) {
        return null;
    }

    @Override
    public Object findBysql(String sql) {
        return null;
    }

    @Override
    public <T> T insertByEntity(T entity) {
        return null;
    }

    @Override
    public <T> T insertByList(T[] entitys) {
        return null;
    }

    @Override
    public <T> int updateByEntity(T entity) {
        return 0;
    }

    @Override
    public int updateByParam(Params params) {
        return 0;
    }

    @Override
    public int updateByHql(String Hql) {
        return 0;
    }

    @Override
    public int updateBysql(String sql) {
        return 0;
    }
}
