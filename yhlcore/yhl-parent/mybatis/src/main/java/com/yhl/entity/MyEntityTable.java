package com.yhl.entity;

import org.apache.ibatis.mapping.ResultMap;

import java.util.List;
import java.util.Set;

public class MyEntityTable {

    //表名
    private String name;
    //注释
    private String catalog;
    //数据库名
    private String schema;
    //创建表时的
    private String orderByClause;
    //可以初始话的时候设置
    private String baseSelect;
    //实体类 => 全部列属性
    private Set<MyEntityColumn> entityClassColumns;
    //实体类 => 主键信息
    private Set<MyEntityColumn> entityClassPKColumns;
    //useGenerator包含多列的时候需要用到
    private List<String> keyProperties;
    private List<String> keyColumns;
    //resultMap对象
    private ResultMap resultMap;
    //类
    private Class<?> entityClass;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getBaseSelect() {
        return baseSelect;
    }

    public void setBaseSelect(String baseSelect) {
        this.baseSelect = baseSelect;
    }

    public Set<MyEntityColumn> getEntityClassColumns() {
        return entityClassColumns;
    }

    public void setEntityClassColumns(Set<MyEntityColumn> entityClassColumns) {
        this.entityClassColumns = entityClassColumns;
    }

    public Set<MyEntityColumn> getEntityClassPKColumns() {
        return entityClassPKColumns;
    }

    public void setEntityClassPKColumns(Set<MyEntityColumn> entityClassPKColumns) {
        this.entityClassPKColumns = entityClassPKColumns;
    }

    public List<String> getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(List<String> keyProperties) {
        this.keyProperties = keyProperties;
    }

    public List<String> getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }
}
