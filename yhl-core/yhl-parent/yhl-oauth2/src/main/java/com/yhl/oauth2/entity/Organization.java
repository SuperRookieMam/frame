package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "organization")
public class Organization extends BaseEntity<String> {

    @Column(name = "name")
    private  String name;
}
