package com.yhl.basedao.dao.Impl;


import com.yhl.basedao.constant.PageInfo;
import com.yhl.basedao.constant.Params;
import com.yhl.basedao.dao.JpaBaseDao;
import com.yhl.basedao.util.ParamUtil;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

//@NoRepositoryBean ：启动时不初始化该实体类。是spring date jpa的一种注解
public class JpaBaseDaoImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID > implements JpaBaseDao<T,ID> {

    private final EntityManager entityManager;
    private Class clazz;
    //父类没有不带参数的构造方法，这里手动构造父类
    public JpaBaseDaoImpl(Class<T> modelClass, EntityManager entityManager) {
        super(modelClass, entityManager);
        this.entityManager = entityManager;
        clazz =modelClass;
    }

    @Override
    public <T> T findById(ID id) {
        return (T) super.findOne(id);
    }

    @Override
    public <T> List<T> findByParams(Params params) {
        String  jpql = ParamUtil.getHqlSelectStr(clazz);
        jpql +=ParamUtil.parseParamToHql(params);
        TypedQuery typedQuery = entityManager.createQuery(jpql,clazz);
        List<T> list=typedQuery.getResultList();
        return list;
    }

    @Override
    public int findCountByParams(Params params) {
        String  jpql =ParamUtil.getHqlSelectCountStr(clazz);
        jpql +=ParamUtil.parseParamToHql(params);
        return (int)entityManager.createQuery(jpql).getSingleResult();
    }

    @Override
    public <T> PageInfo<T> findPageByParams(Params params) {
        PageInfo<T> pageInfo=new PageInfo<>();
        pageInfo.setPageNum(params.getPageNum());
        pageInfo.setPageSize(params.getPageSize());;
        pageInfo.setStartRow(params.getPageNum()*params.getPageSize());
        pageInfo.setEndRow(params.getPageNum()*params.getPageSize()+params.getPageSize());
        pageInfo.setList(findByParams(params));
        pageInfo.setTotal(findCountByParams(params));
        pageInfo.setOrderBy(params.getSort().toString());
        pageInfo.setPages((int) pageInfo.getTotal()/(pageInfo.getPageSize()==0?1:pageInfo.getPageSize()));
        return pageInfo;
    }

    @Override
    public<T1> List<T1>  findByHql(String hql,Class<T1> clazz){
        return entityManager.createQuery(hql,clazz).getResultList();
    }

    @Override
    public<T1>  List<T1>  findBysql(String sql,Class<T1> clazz) {
        return entityManager.createNativeQuery(sql,clazz).getResultList();
    }
    /**
     * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态.
     *和 hibernate 的 save 方法的不同之处: 若对象有 id,
     * 则不能执行 insert 操作, 而会抛出异常
     * */
    @Override
    public <T> T insertByEntity(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <T> int insertByList(T[] entitys) {
        //分批保存相对于速度要块很多
        int batchSize = entitys.length;
        for (int i = 0; i < entitys.length; i++) {
            entityManager.persist(entitys);
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return batchSize;
    }
    /**
     *总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
     * 对象没有id，插入操作
     * 对象有id，且和数据库中有对应的id，修改操作
     * 对象有id，但数据库中找不到对应的id，则抛弃id
     * 进行插入操作entityManager.merge(customer);
     * */

    @Override
    public <T> T updateByEntity(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public int updateByHql(String Hql) {
        return entityManager.createQuery(Hql).executeUpdate();
    }

    @Override
    public int updateBysql(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public void deleteByEntity(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public int deleteByIds(ID[] ids,String IdName) {
        if (ids==null||ids.length==0){
            return 0;
        }
        String jpql =ParamUtil.getHqlDeleteStr(clazz,IdName,ids);
        return entityManager.createQuery(jpql).executeUpdate();
    }
}
