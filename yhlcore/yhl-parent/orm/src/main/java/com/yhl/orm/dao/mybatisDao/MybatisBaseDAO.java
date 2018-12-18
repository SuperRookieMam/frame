package com.yhl.orm.dao.mybatisDao;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.yhl.orm.constant.PageInfo;
import com.yhl.orm.constant.Params;

import java.util.List;

public interface MybatisBaseDAO {
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
    public int findContByParams(Params params);
    /**
     * 分页查询
     * */
    public <T> PageInfo<T> findPageByParams(Params params);
    /**
     *接受类型不顶 ，但是又不能用泛型，到时强转吧
     * */
    public  Object findBysql(String sql);
    /**
     * 根据一个实体插入
     * */
    public <T> T insertByEntity(T entity);

    /**
     * 批量插入
     * */
    public <T> T insertByList(T[] entitys);
    /**
     * 根据实体跟新
     * */
    public<T> int updateByEntity(T entity);
    /**
     * 根据参数跟新
     * */
    public int updateByParam(Params params);

    /**
     *根据sql跟新
     * */
    public int updateBysql(String sql);

}
