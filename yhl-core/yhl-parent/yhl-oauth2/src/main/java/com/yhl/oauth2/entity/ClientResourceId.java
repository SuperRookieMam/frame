package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "client_resource_id")  // 此客户端可以访问的资源地址
public class ClientResourceId extends BaseEntity<String> implements Serializable {

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "resource_id", nullable = false, length = 50)
    private String resource_id;
}
