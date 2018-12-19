package com.yhl.orm.dao.jpaDao;

import com.yhl.orm.constant.PageInfo;
import com.yhl.orm.constant.Params;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 *继承勒JpaRepository的接口，findN那些所有方法，
 *JpaSpecificationExecutor 的执行器所有方法
 * */
@NoRepositoryBean
public interface JpaBaseDao<T,ID extends Serializable>  extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

    /**
     * 自定义接口
     * */
    public <T> T findById(ID id);
    /**
     * 根据参数自定义查询
     * */
    public <T> List<T> findByParams(Params params);
    /**
     * 根据条件查询条数
     * */
    public int findCountByParams(Params params);
    /**
     * 分页查询
     * */
    public <T> PageInfo<T> findPageByParams(Params params);
    /**
     *接受类型不顶 ，但是又不能用泛型，到时强转吧
     * */
    public<T1> List<T1>  findByHql(String hql,Class<T1> clazz);
    /**
     *接受类型不顶 ，但是又不能用泛型，到时强转吧
     * */
    public<T1>  List<T1>  findBysql(String sql,Class<T1> clazz);
    /**
     * 根据一个实体插入
     * */
    public <T> T insertByEntity(T entity);

    /**
     * 批量插入
     * */
    public <T> int insertByList(T[] entitys);
    /**
     * 根据实体跟新
     * */
    public<T> T updateByEntity(T entity);
    /**
     *根据Hql跟新
     * */
    public int updateByHql(String Hql);

    /**
     *根据sql跟新
     * */
    public int updateBysql(String sql);

    /**
     * JpaRepository的接口
     * List<T> findAll();
     * List<T> findAll(Sort var1);
     * List<T> findAll(Iterable<ID> var1);
     * <S extends T> List<S> save(Iterable<S> var1);
     * void flush();
     * T saveAndFlush(T var1);
     * void deleteInBatch(Iterable<T> var1);
     * void deleteAllInBatch();
     * T getOne(ID var1);
     * */
    /**
     * JpaSpecificationExecutor 的接口
     * T findOne(Specification<T> var1);
     * List<T> findAll(Specification<T> var1);
     * Page<T> findAll(Specification<T> var1, Pageable var2);
     * List<T> findAll(Specification<T> var1, Sort var2);
     * int count(Specification<T> var1);
     * */
}
