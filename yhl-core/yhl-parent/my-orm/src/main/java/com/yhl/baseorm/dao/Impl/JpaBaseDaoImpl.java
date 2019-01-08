package com.yhl.baseorm.dao.Impl;


import com.yhl.baseorm.component.constant.Condtion;
import com.yhl.baseorm.component.constant.PageInfo;
import com.yhl.baseorm.component.constant.SelecteParam;
import com.yhl.baseorm.component.constant.UpdateParam;
import com.yhl.baseorm.component.util.MyClassUtil;
import com.yhl.baseorm.dao.JpaBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
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
    public <T> T updateByUpdateParam(UpdateParam updateParams) {
      T entity =(T)  entityManager.find(clazz,updateParams.get("id"));
        entity = UpdateParam.copyPropertis(entity,updateParams,entityManager);
        return entityManager.merge(entity);
    }

    /**
     * 批量跟新
     * */
    @Override
    public <T> int updateByUpdateParams(UpdateParam[] updateParams,int flushSize) {
        Map<String, Field> map = MyClassUtil.getAllFields(clazz);
        for (int i = 0; i < updateParams.length; i++) {
            T entity =(T)  entityManager.find(clazz,updateParams[i].get("id"));
            entity = UpdateParam.copyPropertis(entity,updateParams[i],entityManager,map);
            entityManager.merge(entity);
            if (i%flushSize==0){
                entityManager.flush();
                entityManager.clear();
            }
        }
        return updateParams.length;
    }
    @Override
    public <T> List<T> findByParams(SelecteParam selecteParam) {
        if (selecteParam==null){
            return (List<T>)super.findAll();
        }
        Specification condtion = new Condtion<T>(selecteParam);
         Sort sort = selecteParam.getToSort();
         List<T> list =null;
            if (sort==null){
                list =super.findAll(condtion);
            }else {
                list =super.findAll(condtion,sort);
            }
        return list;
    }

    @Override
    public long findCountByParams(SelecteParam selecteParam) {
        if (selecteParam==null){
            return super.count();
        }
        Specification condtion = new Condtion<T>(selecteParam);
        return super.count(condtion);
    }

    @Override
    public <T> PageInfo<T> findPageByParams(SelecteParam selecteParam) {
        Specification condtion = new Condtion<T>(selecteParam);
        PageRequest pageRequest = new PageRequest(selecteParam.getPageNum() - 1, selecteParam.getPageSize(),selecteParam.getToSort());
        Page page = findAll(condtion, pageRequest);
        PageInfo<T> pageInfo=new PageInfo<>();
        pageInfo.setPageNum(selecteParam.getPageNum());
        pageInfo.setPageSize(selecteParam.getPageSize());
        pageInfo.setStartRow(selecteParam.getPageNum()*selecteParam.getPageSize());
        pageInfo.setEndRow(selecteParam.getPageNum()*selecteParam.getPageSize()+selecteParam.getPageSize());
        pageInfo.setPages((int) pageInfo.getTotal()/(pageInfo.getPageSize()==0?1:pageInfo.getPageSize()));
        pageInfo.setList(page.getContent());
        pageInfo.setTotal(page.getTotalElements());
        pageInfo.setOrderBy(page.getSort().toString());
        return pageInfo;
    }



    /*
    @Override
    public<T1> List<T1>  findByHql(String hql,Class<T1> clazz){
        return entityManager.createQuery(hql,clazz).getResultList();
    }

    @Override
    public<T1>  List<T1>  findBysql(String sql,Class<T1> clazz) {
        return entityManager.createNativeQuery(sql,clazz).getResultList();
    }
    *//**
     * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态.
     *和 hibernate 的 save 方法的不同之处: 若对象有 id,
     * 则不能执行 insert 操作, 而会抛出异常
     * *//*


    *//**
     *总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
     * 对象没有id，插入操作
     * 对象有id，且和数据库中有对应的id，修改操作
     * 对象有id，但数据库中找不到对应的id，则抛弃id
     * 进行插入操作entityManager.merge(customer);
     * *//*



    @Override
    public int updateByHql(String Hql) {
        return entityManager.createQuery(Hql).executeUpdate();
    }

    @Override
    public int updateBysql(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public<T> void deleteByEntity(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public int deleteByIds(ID[] ids,String IdName) {
        if (ids==null||ids.length==0){
            return 0;
        }
        String jpql =ParamUtil.getHqlDeleteStr(clazz,IdName,ids);
        return entityManager.createQuery(jpql).executeUpdate();
    }*/
}
