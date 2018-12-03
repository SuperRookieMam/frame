package demo.dao;

import java.util.List;

public interface BaseDao<T> {

    /**
     * 根据id查询实体
     * @param id
     * @return  T 实体
     * */
    public <T> T findById(String id);

    /**
     * 根据条件查询实体
     * */
    public <T> List<T> findByParams();



}
