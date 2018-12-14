package com.yhl.entity;

import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.entity.EntityTable;

public class MyEntityColumn {
    //如果字段不时引用表的话，不设置
    private EntityTable table;
    //字段名
    private String property;
    //列名
    private String column;
    //字段类型
    private Class<?> javaType;
    //数据库数据类型
    private JdbcType jdbcType;
    // 别名
    private String sequenceName;
    //是否ID
    private boolean id = false;
    //使用UUID
    private boolean uuid = false;
    //索引
    private boolean identity = false;
    //主键自增长
    private String generator;
    //排序
    private String orderBy;
    //可插入
    private boolean insertable = true;
    //可更新
    private boolean updatable = true;
    // 引用关系
    private String ralation;
    //是否忽略
    private  boolean isTransient=false;

    public EntityTable getTable() {
        return table;
    }

    public void setTable(EntityTable table) {
        this.table = table;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isUuid() {
        return uuid;
    }

    public void setUuid(boolean uuid) {
        this.uuid = uuid;
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public String getRalation() {
        return ralation;
    }

    public void setRalation(String ralation) {
        this.ralation = ralation;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean aTransient) {
        isTransient = aTransient;
    }
}
