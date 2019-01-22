package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "client_function")
public class UserFunction extends BaseEntity<String> implements Serializable{
    private static final long serialVersionUID = 6682209155183972522L;

    @Column(name = "function_id")
    private String functionId;

    @Column(name = "rol_id")
    private String rolId;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }
}
