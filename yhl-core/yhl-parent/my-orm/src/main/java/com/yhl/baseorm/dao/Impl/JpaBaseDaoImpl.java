package com.yhl.baseorm.dao.Impl;


import com.yhl.baseorm.component.constant.*;
import com.yhl.baseorm.component.util.MyClassUtil;
import com.yhl.baseorm.component.util.MyQueryUtil;
import com.yhl.baseorm.dao.JpaBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
    /**
     * 根据id查询
     * */
    @Override
    public <T> T findById(ID id) {
        return (T) super.findOne(id);
    }
    /**
     * 单个插入
     * */
    @Override
    public <T> T insertByEntity(T entity) {
        entityManager.persist(entity);
        return entity;
    }
    /**
     * 批量插入
     * */
    @Override
    public <T> int insertByList(List<T> entitys) {
        //分批保存相对于速度要块很多
        int batchSize = entitys.size() - 1;
        for (int i = 0; i < entitys.size(); i++) {
            entityManager.persist(entitys.get(i));
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        return batchSize +1;
    }
    /**
     * 单个跟新
     * */
    @Override
    public <T> T updateByUpdateFields(UpdateFields updateFields) {
      T entity =(T)  entityManager.find(clazz,updateFields.get("id"));
        entity = updateFields.copyPropertis(entity,updateFields,entityManager);
        return entityManager.merge(entity);
    }

    /**
     * 批量跟新
     * */
    @Override
    public <T> int updateByUpdateFields(UpdateFields[] updateFieldss,int flushSize) {
        Map<String, Field> map = MyClassUtil.getAllFields(clazz);
        for (int i = 0; i < updateFieldss.length; i++) {
            T entity =(T)  entityManager.find(clazz,updateFieldss[i].get("id"));
            entity = UpdateFields.copyPropertis(entity,updateFieldss[i],entityManager,map);
            entityManager.merge(entity);
            if (i%flushSize==0){
                entityManager.flush();
                entityManager.clear();
            }
        }
        return updateFieldss.length;
    }
    /**
     * 根据条件跟新某个字段，但不是联表跟新，
     * */
    @Override
    public <T> int updateByWhereCondition (UpdateFields updateFields,WhereCondition whereCondition,int flushSize) {
        Map<String, Field> map = MyClassUtil.getAllFields(clazz);
        List<T> list =findByParams(whereCondition);
        for (int i = 0; i < list.size(); i++) {
            T entity = list.get(i);
            entity = updateFields.copyPropertis(entity,updateFields,entityManager,map);
            entityManager.merge(entity);
            if (i%flushSize==0){
                entityManager.flush();
                entityManager.clear();
            }
        }
        return list.size();
    }

    @Override
    public <T> List<T> findByParams(WhereCondition whereCondition) {
        if (whereCondition==null){
            return (List<T>)super.findAll();
        }
        List<T> list = MyQueryUtil.getTypedQuery(clazz,entityManager,whereCondition).getResultList();
        return list;
    }

    @Override
    public long findCountByWhereCondition(WhereCondition whereCondition) {
        if (whereCondition==null){
            return super.count();
        }
        TypedQuery query =MyQueryUtil.getCountQuery(clazz,entityManager,whereCondition);
        return MyQueryUtil.executeCountQuery(query);
    }

    @Override
    public <T> PageInfo<T> findPageByParams(WhereCondition whereCondition) {
        Page page = MyQueryUtil.readPage(whereCondition,clazz,entityManager);
        PageInfo<T> pageInfo=new PageInfo<>();
        pageInfo.setPageNum(whereCondition.getPageNum());
        pageInfo.setPageSize(whereCondition.getPageSize());
        pageInfo.setStartRow((whereCondition.getPageNum()-1)*whereCondition.getPageSize());
        pageInfo.setEndRow((whereCondition.getPageNum()-1)*whereCondition.getPageSize()+whereCondition.getPageSize());
        pageInfo.setPages((int) pageInfo.getTotal()/(pageInfo.getPageSize()==0?1:pageInfo.getPageSize()));
        pageInfo.setList(page.getContent());
        pageInfo.setTotal(page.getTotalElements());
        pageInfo.setOrderBy(page.getSort().toString());
        return pageInfo;
    }
    /**
     * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态.
     *和 hibernate 的 save 方法的不同之处: 若对象有 id,
     * 则不能执行 insert 操作, 而会抛出异常
     *
     *总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
     * 对象没有id，插入操作
     * 对象有id，且和数据库中有对应的id，修改操作
     * 对象有id，但数据库中找不到对应的id，则抛弃id
     * 进行插入操作entityManager.merge(customer);
     * */
    @Override
    public void deleteById(ID id) {
        super.delete(id);
    }
    @Override
    public int deleteByWhereCondition(WhereCondition whereCondition) {
        List<T> list = this.findByParams(whereCondition);
        super.delete(list);
        return list.size();
    }

    public EntityManager getEntityManager(){
        return  this.entityManager;
    }
    public Class getEntityClass(){
        return  this.clazz;
    }
}
