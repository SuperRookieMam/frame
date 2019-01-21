package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "client_scop")
public class ClientScop extends BaseEntity<String> implements Serializable{
    private static final long serialVersionUID = 6682209155183972522L;



}
